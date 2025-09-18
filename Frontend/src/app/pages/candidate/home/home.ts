import { Component } from '@angular/core';
import { CardModule } from 'primeng/card';
import { FileUpload, FileUploadEvent } from 'primeng/fileupload';
import { UiService } from '../../../services/ui.service';
import { MAX_FILE_SIZE } from '../../../utils/common.util';

@Component({
  selector: 'app-home',
  imports: [CardModule, FileUpload],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

  constructor(public uiService: UiService){}

  public resume:File | undefined;

  onUpload(event: any){
    if (event.files[0].size > MAX_FILE_SIZE) {
      this.uiService.showError("File Size Exceeded");
      return;
    }
    if (event.files[0].type != "application/pdf") {
      this.uiService.showError("Only PDF Files are allowed");
      return;
    }
    console.log(event.files[0].name);
  }
}
