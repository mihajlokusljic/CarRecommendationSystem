import { LoginResponseDTO } from './../../models/LoginResponseDTO';
import { LoginRequestDTO } from './../../models/LoginRequestDTO';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { SnackbarService } from '../../services/snackbar.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  constructor(
    private authService: AuthService,
    private router: Router,
    private snackbarService: SnackbarService
  ) { }

  ngOnInit() {
  }

  onSubmit() {
    const credentials: LoginRequestDTO = {
      username: this.username.value,
      password: this.password.value
    };

    this.authService.attemptAuth(credentials).subscribe(
      (response: LoginResponseDTO) => {
        this.authService.setAuth(response.jsonWebToken);
        this.router.navigate(['/recommendations']);
        this.snackbarService.displayMessage('Login successfull.');
      },

      (error: any) => {
        this.snackbarService.displayMessage('Login failed. Invalid email or password.');
      }
    );
  }

}
