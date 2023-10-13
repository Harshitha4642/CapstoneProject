import { Component } from '@angular/core';
import { CallService } from '../call.service';
import { AppStatus } from 'src/AppStatus';
import { Router } from '@angular/router';

@Component({
  selector: 'app-call',
  templateUrl: './call.component.html',
  styleUrls: ['./call.component.css']
})
export class CallComponent {

constructor(private callService: CallService, private router:Router){}
status: AppStatus = {"status": ""};

createGroupCall() {
  this.callService.saveType("group");
  this.router.navigate(["callForms"]);
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
