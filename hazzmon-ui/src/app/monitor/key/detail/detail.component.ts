import { Component, OnInit } from '@angular/core';
import {KeysService} from '../keys.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html'
})
export class DetailComponent implements OnInit {

  public keyName;
  public keyDetail = {};

  constructor(private route: ActivatedRoute,
              private keysService: KeysService) {
  }

  ngOnInit() {
    this.route.params.subscribe(prms => {
      if (prms['keyName']) {
        this.keyName = prms['keyName'];
        this.loadData(prms['keyName']);
      }
    });
  }

  loadData(key) {
    this.keysService.getKeyDetail(key)
      .subscribe(response => {
        this.keyDetail = response;
      });
  }
}
