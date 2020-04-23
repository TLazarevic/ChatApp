import { Component, OnInit, Inject, Input } from '@angular/core';
import { User } from '../model/user';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  @Input() users: any[];
  selectedUser:User;
  message:String;

  constructor(public dialog: MatDialog) { 
  }

  ngOnInit(): void {
   console.log(this.users)
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
      this.message = result;
      this.send()
    });
  }
  
  send(){
    console.log("sending message:"+this.message+"to"+this.selectedUser.username);
  }

}
