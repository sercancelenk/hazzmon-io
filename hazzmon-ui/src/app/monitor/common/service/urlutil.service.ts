import { Injectable } from '@angular/core';
import {environment} from '../../../../environments/environment';

@Injectable()
export class UrlutilService {
  public baseUrl = '';

  constructor() { }

  getBaseUrl(): String {
    if (environment.production === true) {
        if (this.baseUrl === undefined || this.baseUrl === '') {
            const urlMatches = /(.*)\/hazzmon-ui*/.exec(window.location.href);
            this.baseUrl = urlMatches[1];
        }
    } else {
      this.baseUrl = environment.baseUrl;
    }

    return this.baseUrl;
  }

}
