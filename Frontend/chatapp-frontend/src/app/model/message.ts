import { User } from './user';

export class Message {
    author: User;
    message: string;
    reciever: User;

    constructor(){
        this.author=new User();
        this.message="";
        this.reciever=new User();
      }
  }