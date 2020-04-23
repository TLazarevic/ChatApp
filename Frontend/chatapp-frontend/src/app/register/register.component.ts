import { Component, OnInit } from '@angular/core';
import { RegisterserviceService } from './registerservice.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  username:String;
  password:String;

  constructor(private registerService:RegisterserviceService,private router:Router) { }

  ngOnInit(): void {
  }

  register(){
      this.registerService.register(this.username,this.password).subscribe(data=>{
        alert("Registration sucessful!");
        this.router.navigate(['/login']);
      
      },
         (error: PositionError) => {
          alert("Try again")
        });
      }
  }


