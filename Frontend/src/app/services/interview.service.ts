// src/app/core/services/ui.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface InitiateInterview {
    title: string,
    yourYearsOfExperience: number,
    applyingForPosition: string,
    requiredExperienceForJob: number
}


@Injectable({
    providedIn: 'root',
})
export class InterviewService {

    constructor(private http: HttpClient) { }

    initiateInterview(payload: InitiateInterview): Observable<any> {
        return this.http.post('/interview/v1/initiateInterview', payload).pipe();
    }
}
