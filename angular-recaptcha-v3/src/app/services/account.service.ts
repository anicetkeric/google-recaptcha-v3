import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { ApiResponse } from '../model/api-response';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private isAuthenticated = signal<boolean>(false);
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router); 

  constructor() {
    const user = localStorage.getItem('user');
    if (user) {
      this.isAuthenticated.set(true);
    }
  }
  
  isUserAuthenticated(): boolean {
    return this.isAuthenticated();
  }

  register(user: User, captchaToken: string): Observable<ApiResponse> {
    user.captchaToken = captchaToken;
    return this.http.post<ApiResponse>(`${environment.apiUrl}/register`, user);
  }

  logout(): void {
    // remove user from local storage and set current user to null
    localStorage.removeItem('user');
    this.isAuthenticated.set(false);
    this.router.navigate(['']);
  }
}
