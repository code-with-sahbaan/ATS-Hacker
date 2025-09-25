import { Component } from '@angular/core';
import { UiService } from '../../../services/ui.service';
import { JobService, PostedJob, RecommededResume } from '../../../services/job.service';
import { finalize } from 'rxjs';
import { AccordionModule, AccordionTabOpenEvent } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';
import { ChipModule } from 'primeng/chip';
import { formatDateTime } from '../../../utils/common.util';
import { CardModule } from 'primeng/card';
import { Resume, ResumeService } from '../../../services/resume.service';
import { SkeletonModule } from 'primeng/skeleton';

@Component({
  selector: 'app-home',
  imports: [AccordionModule, ButtonModule, ChipModule, CardModule, SkeletonModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

  public jobs: PostedJob[] = [];
  selectedJob: number = 0;
  public resumes: Resume[] = [];
  fetchinResume: boolean = false;

  constructor(public uiService: UiService, public jobService: JobService, public resumeService: ResumeService) {
    setTimeout(() => {
      this.setHeading();
      this.fetchJobs();
    }, 0);
  }

  setHeading() {
    this.uiService.heading = 'Dashboard';
    this.uiService.subHeading = 'Manage your posted jobs and recommended resumes';
  }

  fetchJobs() {
    this.uiService.showSpinner();
    this.jobService
      .getJobs()
      .pipe(
        finalize(() => {
          // Hiding Loader after API call completion
          this.uiService.hideSpinner();
        })
      )
      .subscribe({
        next: (response) => {
          // Showing success Toast
          this.uiService.showSuccess("Jobs Fetched Successfully");
          const body = response.responseBody;
          this.jobs = body;
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

  fetchResumes(event: AccordionTabOpenEvent) {
    this.selectedJob = event.index;
    this.fetchinResume = true;
    const payload: RecommededResume = {
      jobId : this.selectedJob
    };
    this.jobService
      .getRecommendedResume(payload)
      .pipe(
        finalize(() => {
          // Hiding Loader after API call completion
          this.fetchinResume = false;
        })
      )
      .subscribe({
        next: (response) => {
          // Showing success Toast
          this.uiService.showSuccess("Resumes Fetched Successfully");
          const body = response.responseBody;
          this.resumes = body;
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

  fDt(date: string) {
    return formatDateTime(date);
  }

}
