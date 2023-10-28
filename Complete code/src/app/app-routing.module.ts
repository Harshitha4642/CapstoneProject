import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AppHomeComponent } from './app-home/app-home.component';
import { CallComponent } from './callPage/call.component';
import { MessageComponent } from './message/message.component';
import { CallFormComponent } from './call-form/call-form.component';
import { SearchComponent } from './search/search.component';


const routes: Routes = [
  {path: "", component: LoginComponent},
  
  {path:"appHome", component: AppHomeComponent},
  {path: "call", component: CallComponent},
  {path: "message", component: MessageComponent},
  {path: "callForms", component: CallFormComponent},
  {path: "search", component: SearchComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
