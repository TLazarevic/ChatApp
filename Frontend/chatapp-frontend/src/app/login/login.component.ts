
import { Component, OnInit } from '@angular/core';
import { LoginserviceService } from './loginservice.service';
import { User } from '../model/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username:String
  password:String

  constructor(private loginService:LoginserviceService,private router: Router) {
   }

  ngOnInit(): void {
  }

  logIn(){
    this.loginService.login(this.username,this.password).subscribe(data=>{
      this.loginService.loggedInUser=data
        alert("Login sucessful!");
        this.router.navigate(['/home']);
      
      },
         (error: Error) => {
          alert("Try again")
        });
      }

}
