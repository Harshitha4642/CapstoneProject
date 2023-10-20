import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AppStatus } from 'src/AppStatus';
import { AppType } from 'src/AppType';
import { AppLocation } from './AppLocation';
import { Form } from './Form';
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

// getAllLocations(): Observable<AppLocation[]>
// {
//   const headers = this.getHeaders();
//   return this.http.get<AppLocation[]>(`http://localhost:8080/api/home/allLocations`, {headers});
// }

// getAllCallTypes(): Observable<AppType[]>
// {
//   const headers = this.getHeaders();
//   return this.http.get<AppType[]>(`http://localhost:8080/api/home/allTypes`, {headers});
// }

// saveCDR(appForm: Form): Observable<AppStatus>
// {
//   console.log(appForm);
//   const headers = this.getHeaders();
//   return this.http.post<AppStatus>(`http://localhost:8080/api/home/saveCDR`, appForm , {headers} );
// }

saveMessage(appForm: MessageForm): Observable<AppStatus>
{
  console.log(appForm);
  const headers = this.getHeaders();
  return this.http.post<AppStatus>(`http://localhost:8080/api/message/saveMessage`, appForm , {headers} );
}
}