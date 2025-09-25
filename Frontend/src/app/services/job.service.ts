// src/app/core/services/ui.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Job {
    jobTitle: string,
    responsibilities: string,
    qualifications: string,
    niceToHave: string,
    companyDetails: string
}

export interface PostedJob {
    jobId: number,
    jobTitle: string,
    responsibilities: string,
    qualifications: string,
    niceToHave: string,
    companyDetails: string,
    jobPostedDateTime: Date
}

export interface RecommededResume{
    jobId: number
}

@Injectable({
    providedIn: 'root',
})
export class JobService {

    constructor(private http: HttpClient) { }

    postJob(payload: Job): Observable<any> {
        return this.http.post('/job/v1/postJob', payload).pipe();
    }

    getJobs(): Observable<any> {
        return this.http.get('/job/v1/getJobs').pipe();
    }

    getRecommendedResume(payload: RecommededResume): Observable<any> {
        return this.http.post('/job/v1/getRecommendedResume', payload).pipe();
    }

}
