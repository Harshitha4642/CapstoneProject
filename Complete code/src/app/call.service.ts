import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AppStatus } from 'src/AppStatus';
import { Form } from './Form';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CallService {
 
  type: string = "";
  getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    });
  }

  constructor(private router: Router, private http: HttpClient) { }

  saveType(type: string): void{
    this.type = type;
  }

  getType(): string{
    return this.type;
  }

  createFailedCallService(callForm: Form ): Observable<AppStatus> {
    console.log(callForm);
    const headers = this.getHeaders();
    return this.http.post<AppStatus>("http://localhost:8080/api/home/saveFailedCall", callForm, {headers});
  }
  
  createNormalCallService(callForm: Form) {
    console.log(callForm);
    const headers = this.getHeaders();
    return this.http.post<AppStatus>("http://localhost:8080/api/home/saveNormalCall", callForm, {headers});
  }


}
