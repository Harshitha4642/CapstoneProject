import { Injectable } from '@angular/core';
import { AppLocation } from './AppLocation';
import * as Papa from 'papaparse';
import { Item } from 'src/Item';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  locations: AppLocation[] = [];
  locationStrings : string[] = [];
  location: AppLocation = {
    locationName: '',
    latitude: 0,
    longitude: 0
  };
  locationObjects: AppLocation[] = [];
  constructor() { }

  loadLocationsFromCSV(csvFilePath: string) {
    console.log(" i am called");
    fetch(csvFilePath)
     .then((response) => response.text())
     .then((csvData) => {
       const parsedData = Papa.parse(csvData, { header: true, skipEmptyLines: true });
       if (parsedData && parsedData.data) {
         this.locations = (parsedData.data as Item[]).map((item) => ({
           locationName: item.city + ', ' + item.country,
           latitude: item.lat,
           longitude: item.long,
         }));
         console.log(this.locations);
         const locJSON = JSON.stringify(this.locations);
         localStorage.setItem("processedLocationsCSV", locJSON);
       }
     })
     .catch((error) => {
       console.error('Error fetching CSV data: ', error);
     });
 }

 getLocationStrings(): string[]
 {
  let loc: AppLocation = {
    locationName: "",
    longitude: 0,
    latitude: 0
  };
  
  for(loc of this.locationObjects)
  {
    this.locationStrings.push(loc.locationName);
  }
  return this.locationStrings;
 }

 checkLocations() {
  if(localStorage.getItem("processedLocationsCSV") === 'false'){
    this.loadLocationsFromCSV("/assets/in.csv");
  }
    else
    {
      const loc = localStorage.getItem("processedLocationsCSV");
      if(loc !=null)
        this.locationObjects = JSON.parse(loc);
      else{
        this.loadLocationsFromCSV("/assets/in.csv");
      }
  }
}

getLocationObjects(): AppLocation[]
{
  return this.locationObjects;
}

isValidLocation(location: string): boolean {
  let loc: AppLocation = {
    locationName: "",
    longitude: 0,
    latitude: 0
  };
  
  for(loc of this.locationObjects)
  {
    if(loc.locationName == location)
      return true;
  }
  return false;
}
}

