import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { environment } from '../../../environments/environment';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-sign-in',
  imports: [ButtonModule],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css'
})
export class SignIn {

  constructor(private route: Router) { }

  continueWithGoogle() {
    this.route.navigate(['authorize']);
  }

}
