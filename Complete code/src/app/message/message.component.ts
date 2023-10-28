import { Component, OnInit } from '@angular/core';
import * as Papa from 'papaparse';
import { AppLocation } from '../AppLocation';
import { Form } from '../Form';
import { AppStatus } from 'src/AppStatus';
import { MessageService } from '../message.service';
import { MessageForm } from '../MessageForm';
import { Item } from 'src/Item';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LocationService } from '../location.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
 
  datePipe: any;
  date: Date = new Date();
  username: string | null = localStorage.getItem("username");
  
  create() {
    if (new Date(this.date) > new Date()) {
      this.status.status = "Enter a valid date in the past or today.";
    } 
    if(this.form.subscriber ===0 || this.form.reciever===0){
      this.status.status = "Enter valid ID";
    }
    if(this.form.status === "" || this.form.type === "")
    {
      this.status.status = "Fill in all details";
    }
    if(this.form.time === '')
    {
      this.status.status = "Enter a valid time";
    }
    if(!this.locationService.isValidLocation(this.form.recieverLocation) || !this.locationService.isValidLocation(this.form.subscriberLocation)){
      this.status.status = "Enter a valid location";
    }
    else {
      this.form.date = this.date.toString();
      this.messageService.saveMessage(this.form).subscribe(res => this.status = res);
    }
  }
  
    locations: AppLocation[] = [];
    status: AppStatus = {"status": ""};
    messageType: string[] = ["roaming", "not-roaming"];
    messageStatus: string[] = ["sent", "not-sent"];
  
    form: MessageForm = {
      subscriber: 0, // Example subscriber ID
      reciever: 0,   // Example receiver ID
      date: '', // Example date and time
      time: "00:00",
      subscriberLocation: "", // Example location
      recieverLocation: "",
      type: "",  // Example call type
      status: ""
    };
    
    constructor(
      private messageService: MessageService, 
      private http: HttpClient, 
      private router: Router,
      private locationService: LocationService){}
  
    filteredSubscriberLocations: AppLocation[] = [];
    filteredReceiverLocations: AppLocation[] = [];

  ngOnInit(): void {

    if(this.username === "" || this.username === undefined)
      {
        this.router.navigate(['']);
      }
      this.locationService.checkLocations();
      this.locations = this.locationService.getLocationObjects();
    
  }
  
  filterLocations(inputValue: string, type: 'subscriber' | 'receiver') {
    const filteredLocations = this.locations.filter((location) =>
      location.locationName.toLowerCase().includes(inputValue.toLowerCase())
    );

    if (type === 'subscriber') {
      this.filteredSubscriberLocations = filteredLocations;
    } else if (type === 'receiver') {
      this.filteredReceiverLocations = filteredLocations;
    }
  }

  selectLocation(location: AppLocation, type: 'subscriber' | 'receiver') {
    if (type === 'subscriber') {
      this.form.subscriberLocation = location.locationName;
      this.filteredSubscriberLocations = [];
    } else if (type === 'receiver') {
      this.form.recieverLocation = location.locationName;
      this.filteredReceiverLocations = [];
    }
  }

}


