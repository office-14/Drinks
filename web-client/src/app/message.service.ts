import { Injectable } from '@angular/core';
import {MatSnackBar, MatSnackBarConfig, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from '@angular/material/snack-bar';

@Injectable()
export class MessageService {
  messages: string[] = [];
  action: boolean = true;
  autoHide_success: number = 1500;
  autoHide_error: number = 5000;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  success_extra_class = 'success-message';
  error_extra_class = 'error-message';

  constructor(private _snackBar: MatSnackBar) {}

  show_success(message: string) {
    let config = new MatSnackBarConfig();
    config.verticalPosition = this.verticalPosition;
    config.horizontalPosition = this.horizontalPosition;
    config.duration = this.autoHide_success;
    config.panelClass = this.success_extra_class;
    this._snackBar.open(message, undefined, config);
  }

  show_error(message: string) {
    let config = new MatSnackBarConfig();
    config.verticalPosition = this.verticalPosition;
    config.horizontalPosition = this.horizontalPosition;
    config.duration = this.autoHide_error;
    config.panelClass = this.error_extra_class;
    this._snackBar.open(message, undefined, config);
  }

  clear() {
    this.messages = [];
  }
}