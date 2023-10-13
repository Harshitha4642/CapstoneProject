import { Component, OnInit } from '@angular/core';
import { CallService } from '../call.service';
import { Form } from '../Form';
import { HomeService } from '../home.service';
import { AppLocation } from '../AppLocation';
import { AppStatus } from 'src/AppStatus';

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
  constructor(private callService: CallService, private homeService: HomeService){}
  ngOnInit(): void {
   this.callType = this.callService.getType();
   console.log(this.callType);
   this.homeService.getAllLocations().subscribe( res=> this.locations =res);
  }

  createNormalCall() {
    this.callService.createNormalCallService(this.form).subscribe(res => this.status = res);
  }
    createFailedCall() {
    this.callService.createFailedCallService(this.form).subscribe(res => this.status = res);
  }




}
