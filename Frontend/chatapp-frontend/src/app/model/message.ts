import { User } from './user';

export class Message {
    author: User;
    message: string;
    reciever: User;
    date: Date;
    subject:String;

    constructor(){
        this.author=new User();
        this.message="";
        this.reciever=new User();
        this.subject="";
        this.date=new Date();
      }
  }