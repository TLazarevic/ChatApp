import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { share } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UsersserviceService {

  httpOptionsText = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
  };

 
  constructor(private http: HttpClient) { }

  getLoggedIn(): Observable<User[]>{
    return this.http.get<User[]>('http://localhost:8080/ChatAppWar/rest/users/loggedIn',  this.httpOptionsText)
  }

  getRegistered(): Observable<User[]>{
    return this.http.get<User[]>('http://localhost:8080/ChatAppWar/rest/users/registered',  this.httpOptionsText)
  }
}
