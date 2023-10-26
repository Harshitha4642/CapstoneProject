import { Component, OnInit } from '@angular/core';
import { CallService } from '../call.service';
import { AppStatus } from 'src/AppStatus';
import { Router } from '@angular/router';

@Component({
  selector: 'app-call',
  templateUrl: './call.component.html',
  styleUrls: ['./call.component.css']
})
export class CallComponent implements OnInit {
  constructor(private callService: CallService, private router:Router){}
  username: string | null = localStorage.getItem("username");
  ngOnInit(): void {
      if(this.username === "" || this.username === undefined || this.username === null)
      {
        this.router.navigate(['']);
      }
    }

status: AppStatus = {"status": ""};

createGroupCall() {
  this.callService.saveType("group");
  this.router.navigate(["callForms"]);
  
  //<button (click) = "createGroupCall()">Create Group call</button><br><br>
  //this.callService.createGroupCallService().subscribe(res => this.status = res);
}
createFailedCall() {
  this.callService.saveType("failed");
  this.router.navigate(["callForms"]);
  //this.callService.createFailedCallService().subscribe(res => this.status = res);
}
createNormalCall() {
  this.callService.saveType("normal");
  this.router.navigate(["callForms"]);
  //this.callService.createNormalCallService().subscribe(res => this.status = res);
}

}
