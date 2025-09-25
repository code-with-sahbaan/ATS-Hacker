import { Component } from '@angular/core';
import { UiService } from '../../../services/ui.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Message } from 'primeng/message';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';

@Component({
  selector: 'app-post-job',
  imports: [FormsModule, ReactiveFormsModule, Message, InputTextModule, TextareaModule],
  templateUrl: './post-job.html',
  styleUrl: './post-job.css'
})
export class PostJob {

  public postJobForm: FormGroup;
  readonly MAX_LENGTH = 65535;

  constructor(public uiService: UiService, public fb: FormBuilder) {

    setTimeout(() => this.setHeading(), 0);

    this.postJobForm = fb.group({
      jobTitle: ['', [Validators.required, Validators.maxLength(255)]],
      responsibilities: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
      qualifications: ['', [Validators.required, Validators.maxLength(this.MAX_LENGTH)]],
      niceToHave: ['', [Validators.maxLength(this.MAX_LENGTH)]],
    })

  }

  setHeading() {
    this.uiService.heading = 'Job Posting';
    this.uiService.subHeading = 'Post a job and start hiring';
  }

  get getFormControls() {
    return this.postJobForm?.controls;
  }

  onSubmit() {
    if (this.postJobForm.invalid) {
      return;
    }
    // const payload: UpdateUser = this.updateProfileForm.value;
    // this.uiService.showSpinner();
    // this.userService
    //   .updateUserDetails(payload)
    //   .pipe(
    //     finalize(() => {
    //       // Hiding Loader after API call completion
    //       this.uiService.hideSpinner();
    //     })
    //   )
    //   .subscribe({
    //     next: (response) => {
    //       // Showing success Toast
    //       this.uiService.showSuccess("Profile Details Updated Successfully");
    //     },
    //     error: (error) => {
    //       // Showing error toast
    //       this.uiService.showError(error.error.responseMessage);
    //     },
    //   });
  }

}
