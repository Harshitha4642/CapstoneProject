import { Component, OnInit } from '@angular/core';
import { CallService } from '../call.service';
import { Form } from '../Form';
import { HomeService } from '../home.service';
import { AppLocation } from '../AppLocation';
import { AppStatus } from 'src/AppStatus';
import * as Papa from 'papaparse';
import { Item } from 'src/Item';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router'; 
import { LocationService } from '../location.service';

@Component({
  selector: 'app-call-form',
  templateUrl: './call-form.component.html',
  styleUrls: ['./call-form.component.css']
})
export class CallFormComponent implements OnInit{

  showNumberField = false; 
  callType: string = "";
  types: string[] = ["roaming", "not-roaming"];
  locations: AppLocation[] = [];
  reasons: string[] = ["Declined", "Lines busy", "Missed"];
  status: AppStatus = {"status": ""};
  date: Date = new Date();

  form: Form = 
  {
    subscriber: 0,
    reciever: 0,
    date: "",
    time: "",
    duration: 0,
    subscriberLocation: "",
    recieverLocation: "",
    callType: "",
    reason: "",
    hasVoiceMail: false,
    voiceMailDuration: 0
  }
  
  constructor(
    private callService: CallService, 
    private homeService: HomeService, 
    private http: HttpClient, 
    private router: Router,
    private locationService: LocationService){}
    
  username: string | null = localStorage.getItem("username");
  ngOnInit(): void {
      if(this.username === "" || this.username === undefined)
      {
        this.router.navigate(['']);
      }
      this.callType = this.callService.getType();
      this.locationService.checkLocations();
      this.locations = this.locationService.getLocationObjects();
  
  }

  validateFormInput(type: string) {
    const errorMessages = [];
  
    if (this.form.subscriber === 0 || this.form.reciever === 0) {
      errorMessages.push("Enter valid user ID");
    }
  
    if ((!this.form.reason && type === 'failed' )|| !this.form.callType) {
      errorMessages.push("Enter non-empty values");
    }
  
    if (new Date(this.form.date) > new Date() || this.form.date === '') {
      errorMessages.push("Enter a valid date in the past or today.");
    }

    if(this.form.time === '')
    {
      errorMessages.push("Enter a valid time in the past or today.");
    }
  
    if (!this.locationService.isValidLocation(this.form.recieverLocation) || !this.locationService.isValidLocation(this.form.subscriberLocation)) {
      errorMessages.push("Enter a valid location");
    }
  
    if (errorMessages.length > 0) {
      // If there are error messages, set the status and return without creating a call.
      this.status.status = errorMessages.join("\n");
    } else {
      // If no errors, proceed to create the appropriate call type.
      if (type === 'normal') {
        this.createNormalCall();
      } else {
        this.createFailedCall();
      }
    }
  }

  createNormalCall() {
      this.callService.createNormalCallService(this.form).subscribe(res => this.status = res);
  }
    createFailedCall() {
      this.callService.createFailedCallService(this.form).subscribe(res => this.status = res);
  }

    filteredSubscriberLocations: AppLocation[] = [];
    filteredReceiverLocations: AppLocation[] = [];

  

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

