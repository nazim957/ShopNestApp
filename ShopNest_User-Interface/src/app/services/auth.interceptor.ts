import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private login: LoginService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
  
    //add the jwt token request
    let authReq = request
    const token = this.login.getToken();
   // console.log("inside interceptor")
    if(token!=null)
    {
        authReq=authReq.clone({
          setHeaders:{Authorization :`Bearer ${token}` },
        })
      
        
    }
    return next.handle(authReq);
  }

}

export const AuthInterceptorProviders=[
  {
      provide:HTTP_INTERCEPTORS,
      useClass:AuthInterceptor,
      multi: true,
  },
]

