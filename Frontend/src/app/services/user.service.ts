// src/app/core/services/ui.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { finalize, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { UiService } from './ui.service';

export interface User {
  name: string,
  role: string,
  city: string,
  country: string,
  accessToken: string
}

export interface UpdateUser {
  name: string,
  city: string,
  country: string
}

@Injectable({
  providedIn: 'root',
})
export class UserService {

  constructor(private http: HttpClient, private uiService: UiService) { }

  public currentUser: User | undefined;

  getUserDetails(): Observable<any> {
    return this.http.get('/user/v1/getUserDetails').pipe();
  }

  switchProfile(): Observable<any> {
    return this.http.get('/user/v1/switchProfile').pipe();
  }

  updateUserDetails(payload: UpdateUser): Observable<any> {
    return this.http.post('/user/v1/updateUserDetails', payload).pipe();
  }

  logoutUser(): Observable<any> {
    return this.http.get('/user/logout').pipe();
  }

  logout() {
    this.logoutUser().pipe(
      finalize(() => {
        // Hiding Loader after API call completion
        this.uiService.hideSpinner();
      })
    )
      .subscribe({
        next: (response) => {
          localStorage.clear();
          window.location.href = environment.app_url;
        },
        error: (error) => {
          // Showing error toast
          this.uiService.showError(error.error.responseMessage);
        },
      });
  }

}
