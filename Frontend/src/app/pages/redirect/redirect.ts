import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UiService } from '../../services/ui.service';
import { finalize } from 'rxjs';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-redirect',
  imports: [],
  templateUrl: './redirect.html',
  styleUrl: './redirect.css'
})
export class Redirect implements OnInit {

  constructor(public userService: UserService, public uiService: UiService){}

  ngOnInit(): void {
    setTimeout(() => this.performRedirect(), 0);
  }

  performRedirect() {
    this.uiService.showSpinner();
    this.userService
      .getUserDetails()
      .pipe(
        finalize(() => {
          // Hiding Loader after API call completion
          this.uiService.hideSpinner();
        })
      )
      .subscribe({
        next: (response) => {
          // Showing success Toast
          this.uiService.showSuccess(response.responseMessage);
        },
        error: (error) => {
          // Showing error toast
          window.location.href = environment.loginUrl;
        },
      });
  }

}
