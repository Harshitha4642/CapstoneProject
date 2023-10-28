import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
 
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MessageComponent } from './message/message.component';
import { CallComponent } from './callPage/call.component';
import { HeaderComponent } from './header/header.component';
import { AppHomeComponent } from './app-home/app-home.component';
import { CallFormComponent } from './call-form/call-form.component';
import { SearchComponent } from './search/search.component';
import { SearchResultsComponent } from './search-results/search-results.component';




@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MessageComponent,
    CallComponent,
    HeaderComponent,
    AppHomeComponent,
    CallFormComponent,
    SearchComponent,
    SearchResultsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
