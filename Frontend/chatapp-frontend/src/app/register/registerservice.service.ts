import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterserviceService {


  httpOptionsText = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
    responseType: "text" as "json"
  };

  constructor(private http: HttpClient) { }

  register(username:String,password:String): Observable<User>{
    var user=new User();
    user.username=username;
    user.password=password;
    var userJSON=JSON.stringify(user)

    return this.http.post<User>('http://localhost:8080/ChatAppWar/rest/users/register', userJSON, this.httpOptionsText)
  }
}
