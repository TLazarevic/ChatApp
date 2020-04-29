import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UsersComponent } from '../users/users.component';
import { LoginserviceService } from '../login/loginservice.service';
import { UsersserviceService } from '../users/usersservice.service';
import { Router, NavigationStart, NavigationEnd, RouterEvent } from '@angular/router';
import { Subscription, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';
import { MessagehelperService } from '../messages/messagehelper.service';

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
  isLoaded:boolean=false;
  interval:any

  
  constructor(private loginService:LoginserviceService, private userservice:UsersserviceService,private router: Router,
    private chatService:MessagehelperService) {
    
   }


  ngOnInit(): void {
    this.fetchData();
}

refreshUsers(){
  this.userservice.getLoggedIn().subscribe(data=>{
    this.usersLogged=data
    this.userservice.getRegistered().subscribe(data2=>{
      this.usersRegistered=data2})})
}

fetchData(){

      if (this.loginService.loggedInUser!=null){
        
        this.userservice.getLoggedIn().subscribe(data=>{
          this.usersLogged=data
          this.userservice.getRegistered().subscribe(data2=>{
            this.usersRegistered=data2
            var User=this.loginService.loggedInUser;
            console.log('refreshed')
            this.isLoggedin=true;
            this.isLoaded=true;

            this.chatService.connect();
    
            this.chatService.messages.subscribe(msg => {
              console.log("Response from websocket: " + msg);
              this.refreshUsers();
            });
          })
          
        })
    }
      else
        this.isLoggedin=false;
}

}
