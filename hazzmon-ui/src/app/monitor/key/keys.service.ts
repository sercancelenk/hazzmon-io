import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {UrlutilService} from '../common/service/urlutil.service';

@Injectable()
export class KeysService {

  public LIST_KEYS = '/hazzmon-api/list/keys';
  public KEY_DETAIL = '/hazzmon-api/detail/key/{keyName}';
  public KEY_VALUE = '/hazzmon-api/value/key/{keyName}';
  public EVICT_KEY = '/hazzmon-api/evict'

  constructor(private http: HttpClient,
              private urlUtil: UrlutilService) {

  }

  getKeys(): Observable<any> {
    return this.http
      .get(this.urlUtil.getBaseUrl() + this.LIST_KEYS);
  }

  getKeyDetail(key): Observable<any> {
    return this.http.get(this.urlUtil.getBaseUrl() + this.KEY_DETAIL.replace('{keyName}', key));
  }

  getKeyValue(key): Observable<any> {
      return this.http.get(this.urlUtil.getBaseUrl() + this.KEY_VALUE.replace('{keyName}', key));
  }

  evictKeys(evictRequest): Observable<any> {
    return this.http.post(this.urlUtil.getBaseUrl() + this.EVICT_KEY, evictRequest);
  }

}
