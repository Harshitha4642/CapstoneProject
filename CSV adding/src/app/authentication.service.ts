import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) {}
  authUrl = 'http://localhost:8080/api/auth';

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.authUrl}/token`,{username:username, password:password}).pipe(
      tap(res => {
        localStorage.setItem('token', res['token']);
        localStorage.setItem('username', res['username']);
      })
    );
  }

  checkLoggedInUser(): boolean
  {
    var isLoggedIn: boolean = false;
    var username: string | null = localStorage.getItem("username");
    if (username !== null && username !== "") {
      isLoggedIn = true;
    }
    console.log(isLoggedIn);

    return isLoggedIn;
  }
  
  getUserName(): string | null{
    var username: string | null = localStorage.getItem("username");
    return username;
  }

  logout()
  {
    localStorage.setItem('token', "");
    localStorage.setItem('username', "");
  }
}
