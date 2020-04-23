import { Component, OnInit, Inject, Input } from '@angular/core';
import { User } from '../model/user';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { MessagehelperService, Message } from '../messages/messagehelper.service';

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
  allMessages:String[]=[];
  messageList:boolean=false;

  constructor(public dialog: MatDialog, private chatService:MessagehelperService) { 
    chatService.messages.subscribe(msg => {
      console.log("Response from websocket: " + msg);
      this.allMessages.push(msg)
      if(this.users===undefined)
        this.messageList=true;
    });
  }

  ngOnInit(): void {
  
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
      this.message.author=result.user
      this.send()
    });
  }
  
  send(){
    console.log("new message from client to websocket: ", this.message);
    //this.chatService.messages.next(this.message);
    this.chatService.messages.next(this.message.message);
    this.message.message = "";
  }

}
