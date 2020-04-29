import { Component, OnInit, Inject, Input } from '@angular/core';
import { User } from '../model/user';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { MessagehelperService } from '../messages/messagehelper.service';
import { Message } from '../model/message';
import { LoginserviceService } from '../login/loginservice.service';
import { UsersserviceService } from './usersservice.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  //https://tutorialedge.net/typescript/angular/angular-websockets-tutorial/

  @Input() users: any[];
  selectedUser:User;
  message:Message = new Message();
  allMessages:Message[];
  messageList:boolean=false;
  user:User;
  interval: any;

  constructor(public dialog: MatDialog, private chatService:MessagehelperService, private loginservice:LoginserviceService,private messser:MessagehelperService) { 

  }

  ngOnInit(): void {
    this.fetchData();
    this.interval = setInterval(() => { 
        this.fetchData(); 
    }, 9000);
  }

  fetchData(){
    if(this.users===undefined){
      this.messageList=true;
      this.messser.get().subscribe(data=>{
        this.allMessages=data
        console.log(this.allMessages)
      })
      
      }
  }

  onSelect(user: User): void {
    this.selectedUser = user;
    this.openDialog();
  }

  openDialog(){
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: {user: this.selectedUser}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.message.message = result.message;
      this.message.reciever=result.user
      this.message.author=this.loginservice.loggedInUser
      this.send()
    });
  }
  
  send(){
    // console.log("new message from client to websocket: ", this.message);
    // this.chatService.messages.next(this.message);
    // //this.chatService.messages.next(this.message.message);
    // this.message.message = "";

    this.chatService.send(this.message).subscribe()
  }

}
