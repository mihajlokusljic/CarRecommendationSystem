import { Component, OnInit } from '@angular/core';
import { UserRole } from '../../models/UserRole';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  private currentUserRole = UserRole.UNREGISTERED;

  constructor(
    private authService: AuthService,
  ) { }

  ngOnInit() {
    this.authService.currentUserRole.subscribe(
      (role: UserRole) => {
        this.currentUserRole = role;
      }
    );
    this.authService.populate();
  }

  public onLogout() {
    this.authService.purgeAuth();
  }

  public isAuthenticated(): boolean {
    return this.currentUserRole !== UserRole.UNREGISTERED;
  }

}
