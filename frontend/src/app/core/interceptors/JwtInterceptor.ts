// jwt.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LoginService } from '../services/auth/login.service';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const loginService = inject(LoginService);
  const token = loginService.getToken();

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  return next(req);
};
