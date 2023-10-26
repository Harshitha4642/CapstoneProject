import { Component, OnInit } from '@angular/core';
import { HomeService } from '../home.service';
import { MessageService } from '../message.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CallSearchResult, MessageSearchResult, SearchContent } from '../SearchResult';
import { SearchService } from '../search.service';
import { AppLocation } from '../AppLocation';
import * as Papa from 'papaparse';
import { Item } from 'src/Item';
import { LocationService } from '../location.service';


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
 export class SearchComponent implements OnInit {

  username: string | null = localStorage.getItem("username");
  type: string = "call";
  category: string = "name"; 
  content: string = "";
  input: SearchContent = {type:"call", category: "", content: ""};
  callResults: CallSearchResult[] = [];
  messageResults: MessageSearchResult[] = [];
  isDate: boolean = false;
  customerNames: string[] = [];
  phoneNumbers: string[] = [];
  locations: string[] = [];
  isCall: boolean = true;
  
  location: string = '';
  filteredLocations: string[] = [];
  filteredCustomerNames: string[] = [];
  filteredPhoneNumbers: string[] = [];

  constructor(
    private homeService: HomeService, 
    private messageService: MessageService, 
    private http: HttpClient, 
    private router: Router, 
    private searchService: SearchService,
    private locationService: LocationService
    ){}

  ngOnInit(): void {
    if(this.username === "" || this.username === undefined || this.username === null)
    {
      this.router.navigate(['']);
    }
    this.searchService.getNames().subscribe(res => this.customerNames = res),
    this.searchService.getNumbers().subscribe(res=> this.phoneNumbers = res);
    this.locationService.checkLocations();
    this.locations = this.locationService.getLocationStrings();
    console.log(this.locations);
    }
  search(): void{
    if(this.input.type === "call")
    {
      console.log(this.input);
      this.isCall = true;
      this.searchService.searchCall(this.input).subscribe(res => {this.callResults = res;
      console.log(res)});
    }
    else
    {
      this.isCall = false;
      this.searchService.searchMessage(this.input).subscribe(res => {this.messageResults = res;
      console.log(res)});
    }
    this.input = {type:"call", category: "", content: ""};
  }
  
  displayInputField(isDate: boolean) {
    this.isDate = isDate;
  }
  
  filterSuggestions(inputValue: string) {
    console.log("I am called");
    console.log(this.customerNames);
    const category = this.input.category;
    this.isDate = false;
    if (category === 'customer-name') {
      this.filteredCustomerNames = this.customerNames.filter(name =>
        name.toLowerCase().includes(inputValue.toLowerCase())
      );
      console.log(this.filteredCustomerNames);
    } else if (category === 'customer-number') {
      this.filteredPhoneNumbers = this.phoneNumbers.filter(number =>
        number.includes(inputValue)
      );
    } else if (category === 'location') {
      this.filteredLocations = this.locations.filter(location =>
        location.toLowerCase().includes(inputValue.toLowerCase())
      );
    }
  }

  selectSuggestion(suggestion: string) {
    const category = this.input.category;
    if (category === 'customer-name') {
      this.input.content = suggestion;
      this.filteredCustomerNames = [];
    } else if (category === 'customer-number') {
      this.input.content = suggestion;
      this.filteredPhoneNumbers = [];
    } else if (category === 'location') {
      this.input.content = suggestion;
      this.filteredLocations = [];
    }
  }

}
