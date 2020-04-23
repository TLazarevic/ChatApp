import { Component, ViewChild } from '@angular/core';
import { User } from './model/user';
import { NavbarComponent } from './navbar/navbar.component';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { share } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  @ViewChild('navbar', { static: true }) navbar: NavbarComponent
  logedInUser:User;

  
}
