import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {UrlutilService} from '../common/service/urlutil.service';

@Injectable()
export class KeysService {

  public LIST_KEYS = '/hazzmon-api/list/keys';
  public KEY_DETAIL = '/hazzmon-api/detail/key/{keyName}';

  constructor(private http: HttpClient,
              private urlUtil: UrlutilService) {

  }

  getKeys(): Observable<any> {
    return this.http
      .get(this.urlUtil.getBaseUrl() + this.LIST_KEYS);
  }

  getKeyDetail(key): Observable<any> {
    return this.http.get(this.KEY_DETAIL.replace('{keyName}', key));
  }

}
