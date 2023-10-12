import { Component, OnInit } from '@angular/core';
import { Form } from '../Form';
import { HomeService } from '../home.service';
import { AppLocation } from '../AppLocation';
import { AppType } from 'src/AppType';
import { AppRoutingModule } from '../app-routing.module';
import { AppStatus } from 'src/AppStatus';
import { Time } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  locations: AppLocation[] = [];
  types: AppType[] = [];
  status: AppStatus = {'status': 'not yet done'};

  form: Form = {
    subscriber: 0, // Example subscriber ID
    reciever: 0,   // Example receiver ID
    date: '', // Example date and time
    time: "00:00",
    duration: 0,  // Example duration in "hh:mm" format
    subscriberLocation: "Location1", // Example location
    recieverLocation: "Location2",
    callType: "Voice"  // Example call type
  };
  
  
  constructor(private homeService: HomeService){}
  
  ngOnInit(): void {
    this.homeService.getAllLocations().subscribe(
      res => {
        this.locations = res;
        console.log(this.locations);
      },
      error => {
        console.error("Error:", error);
      }
    );

    this.homeService.getAllCallTypes().subscribe(res => this.types = res);
  }

  create(){
    
    console.log(this.form);
    this.homeService.saveCDR(this.form).subscribe(res=> this.status = res);
  }
}
