import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { MessagehelperService } from '../messages/messagehelper.service';
import { Message } from '../model/message';
import { User } from '../model/user';
import { LoginserviceService } from '../login/loginservice.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  message: Message=new Message();


  constructor(public dialog: MatDialog, private chatService:MessagehelperService,private loginservice:LoginserviceService) { }

  ngOnInit(): void {
  }

  openDialog(){
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: {user:null}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.message.message = result.message;
      this.message.author= this.loginservice.loggedInUser;
      this.message.reciever.username="all"
      this.message.subject=result.subject
      this.message.date=new Date()
      this.send()
    });
  }
  
  send(){
    // console.log("new message from client to websocket: ", this.message);
    // this.chatService.messages.next(this.message);
    // //this.chatService.messages.next(this.message.message);
    // this.message.message = "";
    // this.message.author = new User();
    
    this.chatService.broadcast(this.message).subscribe()
  }

  logout(){
    this.loginservice.logout().subscribe(data=>
      console.log(data))
  }
}
