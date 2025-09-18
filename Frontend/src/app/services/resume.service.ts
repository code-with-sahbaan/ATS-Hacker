// src/app/core/services/ui.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Resume{
    resumeUrl: string,
    resumeName: string,
    lastUpdated: Date
}

@Injectable({
  providedIn: 'root',
})
export class ResumeService {

  constructor(private http: HttpClient) { }

  getResumeDetails(): Observable<any> {
    return this.http.get('/resume/v1/getResumeDetails').pipe();
  }

  updateResume(payload: FormData): Observable<any> {
    return this.http.post('/resume/v1/updateResume', payload).pipe();
  }

}
