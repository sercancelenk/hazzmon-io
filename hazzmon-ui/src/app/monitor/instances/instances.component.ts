import { Component, OnInit } from '@angular/core';
import {InstancesService} from './instances.service';
import {loadConfigurationFromPath} from 'tslint/lib/configuration';

@Component({
  selector: 'app-instances',
  templateUrl: './instances.component.html',
  styleUrls: ['./instances.component.css']
})
export class InstancesComponent implements OnInit {

  public instances: any[] = [];

  constructor(private instancesService: InstancesService) { }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.instancesService.getInstances()
      .subscribe(response => {
        this.instances = response;
      });
  }

}
