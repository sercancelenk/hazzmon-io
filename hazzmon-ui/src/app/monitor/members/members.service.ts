import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {UrlutilService} from '../common/service/urlutil.service';

@Injectable()
export class MembersService {
  public LIST_MEMBERS = '/hazzmon-api/list/names/of/members/by/instance/{instanceName}';

  constructor(private http: HttpClient,
              private urlUtil: UrlutilService) {
  }

  getMembers(instanceName): Observable<any> {
    return this.http
      .get(this.urlUtil.getBaseUrl() + this.LIST_MEMBERS.replace('{instanceName}', instanceName));

  }


}
