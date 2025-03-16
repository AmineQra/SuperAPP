import { HttpInterceptorFn } from '@angular/common/http';
import { environment } from '../../../environments/environment.development';

export const AuthInterceptorService: HttpInterceptorFn = (req, next) => {
  const username = environment.apiAuthUsername;
  const password = environment.apiAuthPassword;

  const clonedRequest = req.clone({
    setHeaders: {
      Authorization: 'Basic ' + btoa(`${username}:${password}`),
    },
  });
  return next(clonedRequest);
};
