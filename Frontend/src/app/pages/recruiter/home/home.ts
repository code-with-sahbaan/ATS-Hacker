import { Component } from '@angular/core';
import { UiService } from '../../../services/ui.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

  constructor(public uiService: UiService){
    uiService.heading = 'Dashboard';
    uiService.subHeading = 'Manage your posted jobs and recommended resumes';
  }

}
