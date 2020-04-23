import { Component, OnInit, Inject } from '@angular/core';
import { User } from '../model/user';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface DialogData {
  message: string;
  user:User;
  
}

@Component({
  selector: 'dialogbox',
  templateUrl: 'dialog.component.html',
})
export class DialogComponent {

  userPresent=true;

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
      if(data.user===null)
        this.userPresent=false;
    }

  onNoClick(): void {
    this.dialogRef.close();
  }

}