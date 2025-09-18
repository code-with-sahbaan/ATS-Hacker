// src/app/core/services/ui.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface User{
    email: string,
    name: string,
    pictureUrl: string,
    provider: string, // GOOGLE, GITHUB, etc.
    role: string,
    city: string,
    country: string,
    skills: string[],
    accessToken: string
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

}
