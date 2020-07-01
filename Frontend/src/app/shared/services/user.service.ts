import { Injectable } from '@angular/core';
import { UserRole } from '../models/UserRole';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUserRole = UserRole.UNREGISTERED;

  constructor(
    private authService: AuthService
  ) {
    this.authService.currentUserRole.subscribe(
      (role: UserRole) => {
        this.currentUserRole = role;
      }
    );
  }

  public isAuthenticated(): boolean {
    return this.currentUserRole !== UserRole.UNREGISTERED;
  }

  public isAdmin(): boolean {
    return this.currentUserRole === UserRole.ADMINISTRATOR;
  }
}
