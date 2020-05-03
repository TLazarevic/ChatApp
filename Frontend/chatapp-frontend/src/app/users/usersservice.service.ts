import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { share } from 'rxjs/operators';
import { GlobalConstants } from '../GlobalConstants';

@Injectable({
  providedIn: 'root'
})
export class UsersserviceService {

  httpOptionsText = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
  };
  global:GlobalConstants=new GlobalConstants

 
  constructor(private http: HttpClient) { }

  getLoggedIn(): Observable<User[]>{
    return this.http.get<User[]>('http://'+this.global.apiURL+':8080/ChatAppWar/rest/users/loggedIn',  this.httpOptionsText)
  }

  getRegistered(): Observable<User[]>{
    return this.http.get<User[]>('http://'+this.global.apiURL+':8080/ChatAppWar/rest/users/registered',  this.httpOptionsText)
  }
}
