import { Injectable, Inject } from '@angular/core';
import { Subject } from 'rxjs';
import { MessagesserviceService } from './messagesservice.service';
import {map} from 'rxjs/operators';
import { Message } from '../model/message';
import { LoginserviceService } from '../login/loginservice.service';

const CHAT_URL = "ws://localhost:8080/ChatAppWar/ws/";



@Injectable({
  providedIn: 'root'
})
export class MessagehelperService {

  public messages: Subject<Message>;
  //public messages: Subject<String>;

  public connect(){
    this.messages = <Subject<Message>>this.wsService.connect(CHAT_URL+this.loginservice.loggedInUser.username).pipe(map(
      // this.messages = <Subject<String>>wsService.connect(CHAT_URL).pipe(map(
         //(response: MessageEvent): String => {
       (response: MessageEvent): Message => {
         let data = JSON.parse(response.data);
         //data 
         return{
          author: data.author,
           message: data.message,
           reciever: data.reciever
        };
       }
     ));
   }
  
  
  constructor( private wsService: MessagesserviceService,private loginservice:LoginserviceService) {
    
   }
  }
