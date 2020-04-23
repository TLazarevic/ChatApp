import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { MessagesserviceService } from './messagesservice.service';
import {map} from 'rxjs/operators';



const CHAT_URL = "ws://localhost:8080/ChatAppWar/ws";

export class Message {
  author: string;
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class MessagehelperService {

  //public messages: Subject<Message>;
  public messages: Subject<String>;
  
  constructor(wsService: MessagesserviceService) {
    //this.messages = <Subject<Message>>wsService.connect(CHAT_URL).pipe(map(
      this.messages = <Subject<String>>wsService.connect(CHAT_URL).pipe(map(
        (response: MessageEvent): String => {
      //(response: MessageEvent): Message => {
        let data = JSON.parse(response.data);
        return data //{
         // author: data.author,
          //message: data.message
          
       // };
      }
    ));
  }
   }

