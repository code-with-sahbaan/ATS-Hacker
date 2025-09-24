import { Component } from '@angular/core';
import { UiService } from '../../../services/ui.service';

@Component({
  selector: 'app-post-job',
  imports: [],
  templateUrl: './post-job.html',
  styleUrl: './post-job.css'
})
export class PostJob {

  constructor(public uiService: UiService){
      uiService.heading = 'Job Posting';
      uiService.subHeading = 'Post a job and start hiring';
    }

}
