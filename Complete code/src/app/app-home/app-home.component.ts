import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-app-home',
  templateUrl: './app-home.component.html',
  styleUrls: ['./app-home.component.css']
})
export class AppHomeComponent implements OnInit {


  constructor(private authService: AuthenticationService, private router: Router) {}
  username: string | null = localStorage.getItem("username");
  ngOnInit(): void {
      if(this.username === "" || this.username === undefined)
      {
        this.router.navigate(['']);
      }
  }

  createMessage(){
    this.router.navigate(["/message"]);
  }
  createCall(){
    this.router.navigate(['/call']);
  }

}
