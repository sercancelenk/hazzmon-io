import { Injectable } from '@angular/core';

@Injectable()
export class UrlutilService {
  public baseUrl = '';

  constructor() { }

  getBaseUrl(): String {
    if (this.baseUrl === undefined || this.baseUrl === '') {
      const urlMatches = /(.*)\/hazzmon-ui*/.exec(window.location.href);
      this.baseUrl = urlMatches[1];
    }
    return this.baseUrl;
  }

}
