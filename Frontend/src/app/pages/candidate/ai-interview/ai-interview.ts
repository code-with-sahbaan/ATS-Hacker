import { Component } from '@angular/core';
import { UiService } from '../../../services/ui.service';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Message } from 'primeng/message';

@Component({
  selector: 'app-ai-interview',
  imports: [InputTextModule, ButtonModule, FormsModule, ReactiveFormsModule, Message],
  templateUrl: './ai-interview.html',
  styleUrl: './ai-interview.css'
})
export class AiInterview {

  aiInterviewForm: FormGroup;
  private mediaRecorder!: MediaRecorder;

  constructor(public uiService: UiService, public fb: FormBuilder) {
    setTimeout(() => this.setHeading(), 0);
    this.aiInterviewForm = fb.group({
      title: ['', Validators.required],
      yourYearsOfExperience: [0, [Validators.required, Validators.min(1)]],
      applyingForPosition: ['', Validators.required],
      requiredExperienceForJob: [0, [Validators.required, Validators.min(1)]]
    })
  }

  setHeading() {
    this.uiService.heading = 'AI Interview';
    this.uiService.subHeading = 'Practice yourself for Interview with AI';
  }

  onSubmit() {

  }

  get getFormControls() {
    return this.aiInterviewForm?.controls;
  }
}
