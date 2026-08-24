import { Component, OnInit } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { environment } from '../../../environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import { getHomePageRedirection, getJWTtoken } from '../../utils/common.util';
import { User, UserService } from '../../services/user.service';
import { UiService } from '../../services/ui.service';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-sign-in',
  imports: [ButtonModule],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css'
})
export class SignIn{

  user: User = {
      name: '',
      role: '',
      city: '',
      country: '',
      accessToken: ''
    }

  constructor(private route: Router, public userService: UserService, public uiService: UiService ) { }

  continueWithGoogle() {
    this.performRedirect();
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
            const body = response.responseBody;
            const token = getJWTtoken();
            this.user = body;
            this.user.accessToken = token;
            localStorage.setItem("USER", JSON.stringify(this.user));
            this.userService.currentUser = body;
            const url = getHomePageRedirection(body);
            this.route.navigate([url]);
          },
          error: (error) => {
            // Showing error toast
           this.uiService.showError("Session Expired!");
           window.location.href = environment.loginUrl;
          },
        });
    }
}
