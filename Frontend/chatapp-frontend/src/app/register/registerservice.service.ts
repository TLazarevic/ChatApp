import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Host } from '../model/host';
import { hostViewClassName } from '@angular/compiler';
import { GlobalConstants } from '../GlobalConstants';

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
  global:GlobalConstants=new GlobalConstants

  constructor(private http: HttpClient) { }
  

  register(username:String,password:String): Observable<User>{
    var user=new User();
    user.username=username;
    user.password=password;
    var host=new Host()
    host.adress="ad"
    host.alias="al"
    user.host=host
    
    var userJSON=JSON.stringify(user)

    return this.http.post<User>('http://'+this.global.apiURL+':8080/ChatAppWar/rest/users/register', userJSON, this.httpOptionsText)
  }
}
