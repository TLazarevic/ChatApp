import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UsersComponent } from '../users/users.component';
import { LoginserviceService } from '../login/loginservice.service';
import { UsersserviceService } from '../users/usersservice.service';
import { Router, NavigationStart, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';

export let browserRefresh = false;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  loggedInUser:User
  isLoggedin:boolean;
  usersLogged:User[] = []
  usersRegistered:User[] = []
  isLoaded:boolean;

  
  constructor(private loginService:LoginserviceService, private userservice:UsersserviceService,private router: Router) {
    
   }

  ngOnInit(): void {

    if (this.loginService.loggedInUser!=null){
      var User=this.loginService.loggedInUser;
      this.isLoggedin=true;

      this.userservice.getLoggedIn().subscribe(data=>{
        this.usersLogged=data
        this.userservice.getLoggedIn().subscribe(data2=>{
          this.usersRegistered=data2
        })
        this.isLoaded=true;
      })
  }
    else
      this.isLoggedin=false;

   
}

}
