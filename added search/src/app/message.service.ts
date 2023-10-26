import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AppStatus } from 'src/AppStatus';
import { MessageForm } from './MessageForm';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

getHeaders(): HttpHeaders {
  return new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  });
}

constructor(private router: Router, private http: HttpClient) { }

saveMessage(appForm: MessageForm): Observable<AppStatus>
{
  console.log(appForm);
  const headers = this.getHeaders();
  return this.http.post<AppStatus>(`http://localhost:8080/api/message/saveMessage`, appForm , {headers} );
}
}