import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CubesAnimation } from '../../components/cubes-animation/cubes-animation';

@Component({
  selector: 'app-sign-in',
  imports: [ButtonModule, CubesAnimation],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css'
})
export class SignIn {

}
