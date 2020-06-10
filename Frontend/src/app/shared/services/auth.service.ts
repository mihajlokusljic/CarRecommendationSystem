import { LoginResponseDTO } from './../models/LoginResponseDTO';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { JwtService } from './jwt.service';
import { LoginRequestDTO } from '../models/LoginRequestDTO';
import { Observable, BehaviorSubject } from 'rxjs';
import { UserRole } from '../models/UserRole';
import { distinctUntilChanged } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly baseUrl = `${environment.baseUrl}/api/auth`;
  private currentUserRoleSubject = new BehaviorSubject<UserRole>({} as UserRole);
  public currentUserRole = this.currentUserRoleSubject.asObservable().pipe(distinctUntilChanged());

  constructor(
    private http: HttpClient,
    private jwtService: JwtService,
  ) { }

  populate() {
    // If JWT detected, attempt to get & store user's info
    const jwt = this.jwtService.getToken();
    if (jwt) {
      this.setAuth(jwt);
    } else {
      // Remove any potential remnants of previous auth states
      this.purgeAuth();
    }
  }

  setAuth(token: string) {
    // Save JWT sent from server in local storage
    this.jwtService.saveToken(token);
    // Set current user role into observable
    const decodedToken = this.jwtService.decodeToken(token);
    switch (decodedToken.role) {
      case 'ROLE_ADMINISTRATOR': this.currentUserRoleSubject.next(UserRole.ADMINISTRATOR); break;
      default: throw new Error('Unknown role');
    }
  }

  purgeAuth() {
    // Remove JWT from local storage
    this.jwtService.destroyToken();
    // Set current user to an empty object
    this.currentUserRoleSubject.next(UserRole.UNREGISTERED as UserRole);
  }

  getAuthToken() {
    return this.jwtService.getToken();
  }

  attemptAuth(loginRequest: LoginRequestDTO): Observable<LoginResponseDTO> {
    return this.http.post<LoginResponseDTO>(this.baseUrl , loginRequest);
  }
}
