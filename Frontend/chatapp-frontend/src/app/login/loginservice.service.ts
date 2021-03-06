import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { IpService } from '../ip.service';
import { HomeComponent } from '../home/home.component';
import { Router } from '@angular/router';
import { Host } from '../model/host';
import { GlobalConstants } from '../GlobalConstants';

@Injectable({
  providedIn: 'root'
})
export class LoginserviceService {

  loggedInUser:User
  global:GlobalConstants=new GlobalConstants

  httpOptionsText = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient, private ip:IpService,private router:Router) { }

  login(username:String,password:String): Observable<User>{
      var user=new User();
      user.username=username;
      user.password=password;
      user.host=new Host();
      var userJSON=JSON.stringify(user)
    return this.http.post<User>('http://'+this.global.apiURL+':8080/ChatAppWar/rest/users/login', userJSON, this.httpOptionsText) 
  
    }

  logout(): Observable<String>{
   var res=this.http.delete<String>('http://'+this.global.apiURL+':8080/ChatAppWar/rest/users/loggedIn/'+this.loggedInUser.username,{ responseType: "text" as "json"})
   this.loggedInUser=null;
   this.router.navigate(['/login'])
    return res;
  }
}
