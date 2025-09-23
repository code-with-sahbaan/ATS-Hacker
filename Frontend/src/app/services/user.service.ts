// src/app/core/services/ui.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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

  constructor(private http: HttpClient) { }

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

}
