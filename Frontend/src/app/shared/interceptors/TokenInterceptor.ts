import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(public auth: AuthService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const jwt = this.auth.getAuthToken();
        if (jwt) {
            // Adds JSON web token (if found) to every requests' header
            request = request.clone({
                setHeaders: {
                Authorization: `Bearer ${jwt}`
                }
            });
        }
        return next.handle(request);
    }
}
