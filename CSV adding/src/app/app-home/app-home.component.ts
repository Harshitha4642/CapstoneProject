import { Component } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-app-home',
  templateUrl: './app-home.component.html',
  styleUrls: ['./app-home.component.css']
})
export class AppHomeComponent {

  constructor(private authService: AuthenticationService, private router: Router) {}

  createMessage(){
    this.router.navigate(["/message"]);
  }
  createCall(){
    this.router.navigate(['/call']);
  }

}
