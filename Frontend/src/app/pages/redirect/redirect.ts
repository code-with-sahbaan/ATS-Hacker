import { Component, OnInit } from '@angular/core';
import { User, UserService } from '../../services/user.service';
import { UiService } from '../../services/ui.service';
import { finalize } from 'rxjs';
import { environment } from '../../../environments/environment';
import { getHomePageRedirection, getJWTtoken } from '../../utils/common.util';
import { Router } from '@angular/router';

@Component({
  selector: 'app-redirect',
  imports: [],
  templateUrl: './redirect.html',
  styleUrl: './redirect.css'
})
export class Redirect implements OnInit {

  user: User = {
    name: '',
    role: '',
    city: '',
    country: '',
    accessToken: ''
  }

  constructor(public userService: UserService, public uiService: UiService, public router: Router) { }

  ngOnInit(): void {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');
    if (token) {
      this.user.accessToken = token;
      localStorage.setItem("USER", JSON.stringify(this.user));
    }
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
          const body = response.responseBody;
          const token = getJWTtoken();
          this.user = body;
          this.user.accessToken = token;
          localStorage.setItem("USER", JSON.stringify(this.user));
          this.userService.currentUser = body;
          const url = getHomePageRedirection(body);
          this.router.navigate([url]);
        },
        error: (error) => {
          // Showing error toast
          window.location.href = environment.loginUrl;
        },
      });
  }

}
