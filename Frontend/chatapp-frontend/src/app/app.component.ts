import { Component, ViewChild } from '@angular/core';
import { User } from './model/user';
import { NavbarComponent } from './navbar/navbar.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  @ViewChild('navbar', { static: true }) navbar: NavbarComponent
  logedInUser:User;
}
