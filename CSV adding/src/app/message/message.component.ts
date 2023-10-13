import { Component } from '@angular/core';
import { HomeService } from '../home.service';
import { AppLocation } from '../AppLocation';
import { Form } from '../Form';
import { AppStatus } from 'src/AppStatus';
import { MessageService } from '../message.service';
import { MessageForm } from '../MessageForm';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent {
create() {
  this.messageService.saveMessage(this.form).subscribe(res => this.status = res);
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
    subscriberLocation: "Location1", // Example location
    recieverLocation: "Location2",
    type: "",  // Example call type
    status: ""
  };
  
  constructor(private homeService: HomeService, private messageService: MessageService){}


    
  ngOnInit(): void {
    this.homeService.getAllLocations().subscribe( res=> this.locations =res);
    //this.homeService.getAllCallTypes().subscribe(res => this.types = res);
  }


}