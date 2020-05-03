import { Host } from './host';

export class User {
    username: String;
    password: String;
    host:Host;

   constructor(){
     this.username="user";
     this.password="pass";
     this.host=new Host();
   }
  }