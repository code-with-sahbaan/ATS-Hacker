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


}
