import { Component, OnInit } from '@angular/core';
import { CardModule } from 'primeng/card';
import { FileUpload, FileUploadEvent } from 'primeng/fileupload';
import { UiService } from '../../../services/ui.service';
import { MAX_FILE_SIZE } from '../../../utils/common.util';
import { Resume, ResumeService } from '../../../services/resume.service';
import { finalize } from 'rxjs';
import { Skeleton } from 'primeng/skeleton';

@Component({
  selector: 'app-home',
  imports: [CardModule, FileUpload, Skeleton],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {

  constructor(public uiService: UiService, public resumeService: ResumeService) { }

  resumeDetails: Resume | undefined;
  resumeLoading: boolean = false;


  ngOnInit(): void {
    setTimeout(() => this.initFetching(), 0);
  }

  initFetching() {
    this.getResumeDetails();
  }

  onUpload(event: any) {
    const file = event.files[0];
    if (file.size > MAX_FILE_SIZE) {
      this.uiService.showError("File Size Exceeded");
      return;
    }
    const payload = new FormData();
    payload.append('resume', file);
    this.resumeLoading = true;
    this.resumeService
      .updateResume(payload)
      .pipe(
        finalize(() => {
          // Hiding Loader after API call completion
          this.resumeLoading = false;
        })
      )
      .subscribe({
        next: (response) => {
          // Showing success Toast
          this.uiService.showSuccess(response.responseMessage);
          const body = response.responseBody;
          this.resumeDetails = body;
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

  getResumeDetails() {
    this.resumeLoading = true;
    this.resumeService
      .getResumeDetails()
      .pipe(
        finalize(() => {
          // Hiding Loader after API call completion
          this.resumeLoading = false;
        })
      )
      .subscribe({
        next: (response) => {
          // Showing success Toast
          this.uiService.showSuccess(response.responseMessage);
          const body = response.responseBody;
          this.resumeDetails = body;
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }
}
