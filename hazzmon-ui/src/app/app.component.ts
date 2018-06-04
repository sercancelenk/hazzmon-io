import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  template: '' +
  '<app-navbar></app-navbar>' +
  '<div class="container-fluid">' +
  '<router-outlet></router-outlet>' +
  '</div>' +
  '<app-footer></app-footer>' +
  '',
})
export class AppComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }
}
