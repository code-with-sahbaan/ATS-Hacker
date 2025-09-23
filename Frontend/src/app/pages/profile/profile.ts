import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ROLE_CANDIDATE } from '../../utils/common.util';
import { InputTextModule } from 'primeng/inputtext';
import { Message } from 'primeng/message';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-profile',
  imports: [FormsModule, ReactiveFormsModule, InputTextModule, Message, ButtonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile {

  updateProfileForm: FormGroup;

  constructor(public fb: FormBuilder) {
    this.updateProfileForm = fb.group({
      name: ['', Validators.required],
      role: [ROLE_CANDIDATE, Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
    })
  }

  get getFormControls() {
    return this.updateProfileForm?.controls;
  }

  onSubmit() {
    if (this.updateProfileForm.invalid) {
      return;
    }
  }

}
