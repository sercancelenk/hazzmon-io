import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {UrlutilService} from '../common/service/urlutil.service';

@Injectable()
export class InstancesService {
  public LIST_INSTANCES = '/hazzmon-api/list/names/of/instances';

  constructor(private http: HttpClient,
              private urlUtil: UrlutilService) {
  }

  getInstances(): Observable<any> {
    return this.http
      .get(this.urlUtil.getBaseUrl() + this.LIST_INSTANCES);
  }

}
