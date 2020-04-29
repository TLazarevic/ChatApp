import { Injectable, Inject } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { MessagesserviceService } from './messagesservice.service';
import {map} from 'rxjs/operators';
import { Message } from '../model/message';
import { LoginserviceService } from '../login/loginservice.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';

const CHAT_URL = "ws://localhost:8080/ChatAppWar/ws/";



@Injectable({
  providedIn: 'root'
})
export class MessagehelperService {

  public messages: Subject<String>;
  //public messages: Subject<String>;

  // public connect(){
  //   this.messages = <Subject<Message>>this.wsService.connect(CHAT_URL+this.loginservice.loggedInUser.username).pipe(map(
  //     // this.messages = <Subject<String>>wsService.connect(CHAT_URL).pipe(map(
  //        //(response: MessageEvent): String => {
  //      (response: MessageEvent): Message => {
  //        let data = JSON.parse(response.data);
  //        //data 
  //        return{
  //         author: data.author,
  //          message: data.message,
  //          reciever: data.reciever,
  //          date:new Date(),
  //          subject: data.subject,

  //       };
  //      }
  //    ));
  //  }


  public connect(){
    this.messages = <Subject<String>>this.wsService.connect(CHAT_URL+this.loginservice.loggedInUser.username).pipe(map(
             (response: MessageEvent): String => {
             //let data = JSON.parse(response.data);
             return response.data
           }
         ));
   }

  httpOptionsText = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  httpOptionsText2 = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
    responseType: "text" as "json"
  };


  
  public send(message:Message){

    var messageJSON=JSON.stringify(message)
    return this.http.post<Response>('http://localhost:8080/ChatAppWar/rest/messages/user', messageJSON, this.httpOptionsText)
  }

  public broadcast(message:Message){

    var messageJSON=JSON.stringify(message)
    return this.http.post<Response>('http://localhost:8080/ChatAppWar/rest/messages/all', messageJSON, this.httpOptionsText)
  }

 

  public get(): Observable<Message[]>{
    return this.http.get<Message[]>('http://localhost:8080/ChatAppWar/rest/messages/'+this.loginservice.loggedInUser.username, this.httpOptionsText)
  }
  
  
  constructor( private wsService: MessagesserviceService,private loginservice:LoginserviceService,private http: HttpClient) {
    
   }
  }
