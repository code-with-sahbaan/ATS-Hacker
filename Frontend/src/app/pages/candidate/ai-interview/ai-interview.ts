import { Component } from '@angular/core';
import { UiService } from '../../../services/ui.service';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Message } from 'primeng/message';
import { InitiateInterview, InterviewService } from '../../../services/interview.service';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-ai-interview',
  imports: [InputTextModule, ButtonModule, FormsModule, ReactiveFormsModule, Message],
  templateUrl: './ai-interview.html',
  styleUrl: './ai-interview.css'
})
export class AiInterview {

  aiInterviewForm: FormGroup;
  interviewInitiated: boolean = false;
  private mediaRecorder!: MediaRecorder;
  private audioChunks: Blob[] = [];
  isRecording = false;
  audioBlob: Blob | null = null;

  constructor(public uiService: UiService, public fb: FormBuilder, public interviewService: InterviewService) {
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
    this.uiService.showSpinner();
    const payload: InitiateInterview = this.aiInterviewForm.value;
    this.interviewService
      .initiateInterview(payload)
      .pipe(
        finalize(() => {
          // Hiding Loader after API call completion
          this.uiService.hideSpinner();
        })
      )
      .subscribe({
        next: (response) => {
          this.playAudio(response);
          this.interviewInitiated = true;
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

  playAudio(data: any) {
    const blob = new Blob([data], { type: 'audio/mpeg' });
    const url = URL.createObjectURL(blob);
    const audio = new Audio(url);
    audio.play();

    audio.onended = ()=>{
      this.startRecording();
    }
  }

  async startRecording() {
    this.isRecording = true;
    this.audioChunks = [];
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    this.mediaRecorder = new MediaRecorder(stream);

    this.mediaRecorder.ondataavailable = (event) => {
      console.log("data available");
      if (event.data.size > 0) {
        this.audioChunks.push(event.data);
      }
    };

    this.mediaRecorder.onstop = () => {
      this.audioBlob = new Blob(this.audioChunks, { type: 'audio/webm' });
    };

    this.mediaRecorder.start();
  }

  stopRecording() {
    this.isRecording = false;
    this.mediaRecorder.stop();
    this.sendRecording();
  }

  sendRecording() {
    if (!this.audioBlob){
      console.log("data not available");
      return;
    };

    const formData = new FormData();
    formData.append('answer', this.audioBlob, 'recording.webm');

    const payload = formData;
    this.interviewService
      .getReply(payload)
      .pipe(
        finalize(() => {
          // Hiding Loader after API call completion
          this.uiService.hideSpinner();
        })
      )
      .subscribe({
        next: (response) => {
          this.playAudio(response);
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

  get getFormControls() {
    return this.aiInterviewForm?.controls;
  }
}
