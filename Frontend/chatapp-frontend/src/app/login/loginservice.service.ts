import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginserviceService {

  loggedInUser:User

  httpOptionsText = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
  };


  
  constructor(private http: HttpClient) { }

  login(username:String,password:String): Observable<User>{
    var user=new User();
    user.username=username;
    user.password=password;
    var userJSON=JSON.stringify(user)

    return this.http.post<User>('http://localhost:8080/ChatAppWar/rest/users/login', userJSON, this.httpOptionsText)
  }
}
