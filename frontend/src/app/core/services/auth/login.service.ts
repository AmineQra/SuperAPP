import { HttpClient } from '@angular/common/http';
import { Injectable, Optional } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private apiServerUrl: string = environment.apiServerUrl;
  private tokenSubject: BehaviorSubject<string | null>;
  public authToken: Observable<string | null>;

  constructor(private http: HttpClient, private router: Router) {
    this.tokenSubject = new BehaviorSubject<string | null>(
      localStorage.getItem('access_token')
    );
    this.authToken = this.tokenSubject.asObservable();
  }

  get tokenValue(): string | null {
    return this.tokenSubject.value;
  }

  public authenticate(email: string, password: string): Observable<any> {
    return this.http
      .post<any>(`${this.apiServerUrl}/api/auth/login`, {
        email,
        password,
      })
      .pipe(
        tap((response) => {
          if (response.token) {
            this.storeToken(response.token);
          }
        })
      );
  }

  public storeToken(token: string): void {
    localStorage.setItem('access_token', token);
    this.tokenSubject.next(token);
  }

  public logout(): void {
    localStorage.removeItem('access_token');
    this.tokenSubject.next(null);
    this.router.navigate(['']);
  }

  public isLoggedIn(): boolean {
    return !!this.tokenValue && !this.isTokenExpired();
  }

  private isTokenExpired(): boolean {
    const token = this.tokenValue;
    if (!token) return true;

    const expiry = JSON.parse(atob(token.split('.')[1])).exp;
    return Math.floor(new Date().getTime() / 1000) >= expiry;
  }

  public getToken(): string | null {
    return this.tokenValue;
  }
}
