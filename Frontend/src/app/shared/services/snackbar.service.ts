import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  private duration = 2000;

  constructor(
    private snackBar: MatSnackBar
  ) { }

  public displayMessage(message: string, closeButtonMessage: string = 'Close') {
    this.snackBar.open(message, closeButtonMessage, {
      duration: this.duration
    });
  }
}
