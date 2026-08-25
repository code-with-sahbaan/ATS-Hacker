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
export class SignIn implements OnInit {

  user: User = {
      name: '',
      role: '',
      city: '',
      country: '',
      accessToken: ''
    }

  constructor(private route: Router, public userService: UserService, public uiService: UiService ) { }

  ngOnInit(): void {
    setTimeout(() => this.performRedirect(), 0)
  }
  continueWithGoogle() {
    this.auth();
  }

  auth(){
    this.uiService.showSpinner();
      this.userService
        .auth()
        .pipe(
          finalize(() => {
            // Hiding Loader after API call completion
            this.uiService.hideSpinner();
          })
        )
        .subscribe({
          next: (response) => {
            // Showing success Toast
            this.uiService.showSuccess("Welcome Again")
          },
          error: (error) => {
            // Showing error toast
           window.location.href = environment.loginUrl;
          },
        });
    }


  performRedirect() {
    try {
      this.user = JSON.parse(localStorage.getItem("USER")!)
      const url = getHomePageRedirection(this.user);
      this.route.navigate([url]);
    } catch (error) {
      this.uiService.showError("Session Expired! Please re-login");
    }
  }
}
