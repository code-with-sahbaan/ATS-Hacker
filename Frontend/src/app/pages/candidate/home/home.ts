import { Component, OnInit } from '@angular/core';
import { CardModule } from 'primeng/card';
import { FileUpload, FileUploadEvent } from 'primeng/fileupload';
import { UiService } from '../../../services/ui.service';
import { formatDateTime, MAX_FILE_SIZE } from '../../../utils/common.util';
import { Resume, ResumeService } from '../../../services/resume.service';
import { finalize } from 'rxjs';
import { Skeleton } from 'primeng/skeleton';
import { ChipModule } from 'primeng/chip';
import { AccordionModule } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';
import { JobService, PostedJob } from '../../../services/job.service';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-home',
  imports: [CardModule, FileUpload, Skeleton, ChipModule, AccordionModule, ButtonModule, DialogModule, ChipModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {
  
  resumeDetails: Resume | undefined;
  resumeLoading: boolean = false;
  jobsLoading: boolean = false;
  visibleJobDialog: boolean = false;
  jobs: PostedJob[] = [];
  selectedJob: PostedJob | undefined;

  constructor(public uiService: UiService, public resumeService: ResumeService, public jobService: JobService) {
    setTimeout(() => this.setHeading(), 0);
  }

  setHeading() {
    this.uiService.heading = 'Dashboard';
    this.uiService.subHeading = 'Manage your resume and recommended Jobs';
  }

  ngOnInit(): void {
    setTimeout(() => this.initFetching(), 0);
  }

  initFetching() {
    this.getResumeDetails();
    this.getRecommendedJobs();
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
          this.getRecommendedJobs()
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

  getRecommendedJobs() {
    this.jobsLoading = true;
    this.resumeService
      .getRecommendedJobs()
      .pipe(
        finalize(() => {
          // Hiding Loader after API call completion
          this.jobsLoading = false;
        })
      )
      .subscribe({
        next: (response) => {
          // Showing success Toast
          this.uiService.showSuccess(response.responseMessage);
          const body = response.responseBody;
          this.jobs = body;
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

  fDt(date: string | undefined) {
    return formatDateTime(date!);
  }

  showJobDialog(job: PostedJob){
    this.selectedJob = job;
    this.visibleJobDialog = true;
  }
}
