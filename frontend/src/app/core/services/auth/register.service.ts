import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  private apiServerUrl: string = environment.apiServerUrl;

  constructor(private http: HttpClient) {}

  public register(
    firstName: string,
    lastName: string,
    email: string,
    password: string
  ) {
    return this.http
      .post<any>(`${this.apiServerUrl}/api/auth/register`, {
        firstName,
        lastName,
        email,
        password,
      })
      .pipe(
        tap((response) => {
          if (response.token) {
            localStorage.setItem('access_token', response.token);
          }
        })
      );
  }
}
