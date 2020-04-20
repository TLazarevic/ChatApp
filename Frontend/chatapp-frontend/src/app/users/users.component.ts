import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { users } from '../mock-users';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users:User[];
  selectedUser:User;
  message:String;

  constructor() { 
   
  }

  ngOnInit(): void {
    this.users=users;
    console.log(users)
  }

  onSelect(user: User): void {
    this.selectedUser = user;
  }
  
  send(){
    console.log("sending message:"+this.message+"to"+this.selectedUser.username);
  }

}
