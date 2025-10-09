import { Component, NgZone } from '@angular/core';
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
  isInterviewEnded: boolean = false;

  constructor(public uiService: UiService, public fb: FormBuilder, public interviewService: InterviewService, public zone: NgZone) {
    setTimeout(() => this.setHeading(), 0);
    this.aiInterviewForm = fb.group({
      title: ['', Validators.required],
      yourYearsOfExperience: [1, [Validators.required, Validators.min(1)]],
      applyingForPosition: ['', Validators.required],
      requiredExperienceForJob: [1, [Validators.required, Validators.min(1)]]
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
      this.zone.run(()=>{
        this.isRecording = true;
      })
      this.startRecording();
    }
  }

  async startRecording() {
    this.audioChunks = [];
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    this.mediaRecorder = new MediaRecorder(stream);

    this.mediaRecorder.ondataavailable = (event) => {
      if (event.data.size > 0) {
        this.audioChunks.push(event.data);
      }
    };

    this.mediaRecorder.onstop = () => {
      this.audioBlob = new Blob(this.audioChunks, { type: 'audio/webm' });
      this.sendRecording();
    };

    this.mediaRecorder.start();
  }

  stopRecording() {
    this.isRecording = false;
    this.mediaRecorder.stop();
  }

  endInterview(){
    this.stopRecording();
    this.interviewInitiated = false;
    this.isInterviewEnded = true;
  }

  sendRecording() {
    if (!this.audioBlob){
      return;
    };
    if(this.isInterviewEnded){
      this.mediaRecorder.stream.getTracks().forEach(track => track.stop());
      this.isInterviewEnded = false;
      return;
    }

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
