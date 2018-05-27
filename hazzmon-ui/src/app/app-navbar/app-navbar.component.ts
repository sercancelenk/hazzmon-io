import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-navbar',
  templateUrl: 'app-navbar.component.html',
  styles: []
})
export class AppNavbarComponent implements OnInit {

  public appVersion: string = '';
  constructor() { }

  ngOnInit() {
    this.appVersion = environment.VERSION;
  }

}
