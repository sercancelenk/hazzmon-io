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
  public keyValue = '';
  public valueItemsHidden = true;


  constructor(private route: ActivatedRoute,
              private keysService: KeysService) {
  }

  ngOnInit() {
    this.route.params.subscribe(prms => {
      if (prms['keyName']) {
        this.keyName = prms['keyName'];
        this.loadData();
      }
    });
  }

  loadData() {
    this.keysService.getKeyDetail(this.keyName)
      .subscribe(response => {
        this.keyDetail = response;
      });
  }

  getValueOfKey() {
      this.keysService.getKeyValue(this.keyName)
          .subscribe(response => {
            if (response !== undefined) {
                this.valueItemsHidden = false;
                this.keyValue = response.value;
            }
          });
  }

  clearValue() {
    this.keyValue = '';
    this.valueItemsHidden = true;
  }
}
