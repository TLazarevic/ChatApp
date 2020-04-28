import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { IpService } from '../ip.service';

@Injectable({
  providedIn: 'root'
})
export class LoginserviceService {

  loggedInUser:User

  httpOptionsText = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient, private ip:IpService) { }

  login(username:String,password:String): Observable<User>{
      var user=new User();
      user.username=username;
      user.password=password;
      user.host=""
      var userJSON=JSON.stringify(user)

    return this.http.post<User>('http://localhost:8080/ChatAppWar/rest/users/login', userJSON, this.httpOptionsText) 
  
    }

  logout(): Observable<String>{
   var res=this.http.delete<String>('http://localhost:8080/ChatAppWar/rest/users/loggedIn/'+this.loggedInUser.username,{ responseType: "text" as "json"})
   this.loggedInUser=null;
    return res;
  }
}
