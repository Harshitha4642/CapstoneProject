import { Component, OnInit } from '@angular/core';
import * as Papa from 'papaparse';
import { HomeService } from '../home.service';
import { AppLocation } from '../AppLocation';
import { Form } from '../Form';
import { AppStatus } from 'src/AppStatus';
import { MessageService } from '../message.service';
import { MessageForm } from '../MessageForm';
import { Item } from 'src/Item';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
 
  datePipe: any;
  date: Date = new Date();
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
    if(!this.isValidLocation(this.form.recieverLocation) || !this.isValidLocation(this.form.subscriberLocation)){
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
      subscriberLocation: "Location1", // Example location
      recieverLocation: "Location2",
      type: "",  // Example call type
      status: ""
    };
    
    constructor(private homeService: HomeService, private messageService: MessageService, private http: HttpClient){}
  
    filteredSubscriberLocations: AppLocation[] = [];
    filteredReceiverLocations: AppLocation[] = [];

  ngOnInit(): void {
    if(localStorage.getItem("processedLocationsCSV") === 'false'){
      this.loadLocationsFromCSV("/assets/in.csv");
    }
      else
      {
        const loc = localStorage.getItem("processedLocationsCSV");
        if(loc !=null)
          this.locations = JSON.parse(loc);
        else{
          this.loadLocationsFromCSV("/assets/in.csv");
        }
    }
  }
  

  loadLocationsFromCSV(csvFilePath: string) {
    // Assuming you have a way to fetch the CSV data using the provided path
    // You can use the fetch API or any method that suits your application to get the CSV data.
    fetch(csvFilePath) // Replace with your method to fetch the CSV data
      .then((response) => response.text())
      .then((csvData) => {
        const parsedData = Papa.parse(csvData, { header: true, skipEmptyLines: true });
        if (parsedData && parsedData.data) {
          this.locations = (parsedData.data as Item[]).map((item) => ({
            locationName: item.city + ', ' + item.country,
            latitude: item.lat,
            longitude: item.long,
          }));
          //console.log(this.locations);
          const locJSON = JSON.stringify(this.locations);
          localStorage.setItem("processedLocationsCSV", locJSON);
        }
      })
      .catch((error) => {
        console.error('Error fetching CSV data: ', error);
      });
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

  isValidLocation(location: string): boolean {
    let loc: AppLocation = {
      locationName: "",
      longitude: 0,
      latitude: 0
    };
    
    for(loc of this.locations)
    {
      if(loc.locationName == location)
        return true;
    }
    return false;
  }
}


