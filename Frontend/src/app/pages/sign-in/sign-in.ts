import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { environment } from '../../../environments/environment';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sign-in',
  imports: [ButtonModule],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css'
})
export class SignIn {

  constructor(private route: ActivatedRoute) { }

  continueWithGoogle() {
    window.location.href = "https://accounts.google.com/o/oauth2/v2/auth?redirect_uri=" + environment.redirectURL + "&response_type=code&client_id=" + environment.clientId + "&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+openid&access_type=offline"
  }

  ngOnInit(): void {
    // Route parameters (e.g., /users/:id)
    this.route.params.subscribe(params => {
      console.log('Route Params:', params); // e.g., { id: '123' }
    });

    // Query parameters (e.g., ?search=test)
    this.route.queryParams.subscribe(queryParams => {
      console.log('Query Params:', queryParams); // e.g., { search: 'test' }
    });
  }

}
