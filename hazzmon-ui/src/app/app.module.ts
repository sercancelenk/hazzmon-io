import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { AppNavbarComponent } from './app-navbar/app-navbar.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AppRoutingModule} from './app.routing';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {KeysService} from './monitor/key/keys.service';
import {CommonModule} from '@angular/common';
import {MembersService} from './monitor/members/members.service';
import {InstancesService} from './monitor/instances/instances.service';
import {UrlutilService} from './monitor/common/service/urlutil.service';
import {PrettyJsonModule} from 'angular2-prettyjson';
import {ConfirmationPopoverModule} from "angular-confirmation-popover";

@NgModule({
  declarations: [AppComponent, AppNavbarComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CommonModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule.forRoot(),
      PrettyJsonModule,
  ],
  providers: [KeysService, MembersService, InstancesService, UrlutilService],
  bootstrap: [AppComponent]
})
export class AppModule {}
