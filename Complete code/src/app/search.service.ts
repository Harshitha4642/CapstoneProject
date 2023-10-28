import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CallSearchResult, MessageSearchResult, SearchContent } from './SearchResult';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  
getHeaders(): HttpHeaders {
  return new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  });
}

searchCall(search : SearchContent): Observable<CallSearchResult[]>{
  console.log(search);
  const headers = this.getHeaders();
  return this.http.post<CallSearchResult[]>("http://localhost:8080/api/search/getCallRecords", search ,{headers});
}

searchMessage(search : SearchContent): Observable<MessageSearchResult[]>{
  const headers = this.getHeaders();
  return this.http.post<MessageSearchResult[]>("http://localhost:8080/api/search/getMessageRecords", search ,{headers});
}

getNames(): Observable<string[]> {
  const headers = this.getHeaders();
  return this.http.get<string[]>("http://localhost:8080/api/search/getNames", {headers});
}

getNumbers(): Observable<string[]> {
  const headers = this.getHeaders();
  return this.http.get<string[]>("http://localhost:8080/api/search/getNumbers", {headers});
}

constructor(private router: Router, private http: HttpClient) { }

}
