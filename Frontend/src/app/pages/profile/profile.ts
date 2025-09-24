import { Component, OnInit } from '@angular/core';
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
import { UpdateUser, UserService } from '../../services/user.service';
import { UiService } from '../../services/ui.service';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-profile',
  imports: [FormsModule, ReactiveFormsModule, InputTextModule, Message, ButtonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile implements OnInit {

  updateProfileForm: FormGroup;

  constructor(public fb: FormBuilder, public userService: UserService, public uiService: UiService) {
    this.updateProfileForm = fb.group({
      name: ['', Validators.required],
      city: ['', Validators.required],
      role: ['', Validators.required],
      country: ['', Validators.required],
    });
    uiService.heading = 'Profile';
    uiService.subHeading = 'Manage your personal details and profile';
  }

  ngOnInit(): void {
    setTimeout(() => this.fetchUserDetails(), 0)
  }

  get getFormControls() {
    return this.updateProfileForm?.controls;
  }

  onSubmit() {
    if (this.updateProfileForm.invalid) {
      return;
    }
    const payload: UpdateUser = this.updateProfileForm.value;
    this.uiService.showSpinner();
    this.userService
      .updateUserDetails(payload)
      .pipe(
        finalize(() => {
          // Hiding Loader after API call completion
          this.uiService.hideSpinner();
        })
      )
      .subscribe({
        next: (response) => {
          // Showing success Toast
          this.uiService.showSuccess("Profile Details Updated Successfully");
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

  isCandidate() {
    return this.updateProfileForm.get('role')?.value == ROLE_CANDIDATE;
  }

  switchProfile() {
    this.uiService.showSpinner();
    this.userService
      .switchProfile()
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
          window.location.href = body;
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

  fetchUserDetails() {
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
          this.updateProfileForm.setValue(body);
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

}
