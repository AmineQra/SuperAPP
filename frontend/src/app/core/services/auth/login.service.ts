import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private apiServerUrl: string = environment.apiServerUrl;
  private tokenSubject: BehaviorSubject<string | null>;
  public authToken: Observable<string | null>;
  private isBrowser: boolean;

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);

    // Initialize with safe value
    const initialToken = this.isBrowser
      ? localStorage.getItem('access_token')
      : null;
    this.tokenSubject = new BehaviorSubject<string | null>(initialToken);
    this.authToken = this.tokenSubject.asObservable();
  }

  get tokenValue(): string | null {
    return this.tokenSubject.value;
  }

  public authenticate(email: string, password: string): Observable<any> {
    return this.http
      .post<any>(`${this.apiServerUrl}/api/auth/login`, { email, password })
      .pipe(
        tap((response) => {
          if (response.token) {
            this.storeToken(response.token);
          }
        })
      );
  }

  public storeToken(token: string): void {
    if (this.isBrowser) {
      localStorage.setItem('access_token', token);
    }
    this.tokenSubject.next(token);
  }

  public logout(): void {
    if (this.isBrowser) {
      localStorage.removeItem('access_token');
    }
    this.tokenSubject.next(null);
    this.router.navigate(['']);
  }

  public isLoggedIn(): boolean {
    return !!this.tokenValue && !this.isTokenExpired();
  }

  private isTokenExpired(): boolean {
    const token = this.tokenValue;
    if (!token) return true;

    try {
      if (this.isBrowser) {
        const expiry = JSON.parse(atob(token.split('.')[1])).exp;
        return Math.floor(Date.now() / 1000) >= expiry;
      }
      return true; // Treat as expired if not in browser
    } catch (e) {
      return true;
    }
  }

  public getToken(): string | null {
    return this.tokenValue;
  }
}
