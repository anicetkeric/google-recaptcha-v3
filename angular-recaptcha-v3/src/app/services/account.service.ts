import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { User } from '../model/user';
import { environment } from 'src/environments/environment';
import { ApiResponse } from '../model/api-response';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;

  

  constructor(
    private router: Router,
    private http: HttpClient
) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
}
public get userValue(): User {
  return this.userSubject.value;
}


register(user: User, token: string) {
  user.captchaToken = token;
  return this.http.post<ApiResponse>(`${environment.apiUrl}/api/register`, user)
      .pipe(map(data => {
           var response = <ApiResponse> data; 
          // store user details in local storage
          localStorage.setItem('user', JSON.stringify(response.data));
          this.userSubject.next(response.data);
          return user;
      }));
}

logout() {
  // remove user from local storage and set current user to null
  localStorage.removeItem('user');
  this.userSubject.next(null!);
  this.router.navigate(['']);
}

}
