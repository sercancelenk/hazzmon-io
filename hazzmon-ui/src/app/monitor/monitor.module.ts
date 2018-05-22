import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MonitorRoutingModule } from './monitor-routing.module';
import { MonitorComponent } from './monitor.component';
import { HomeComponent } from './home/home.component';
import {DataFilterPipe} from '../data-filter.pipe';
import {ButtonModule} from 'primeng/button';
import {CardModule} from 'primeng/card';
import {DataTableModule} from 'primeng/primeng';
import { MembersComponent } from './members/members.component';
import { InstancesComponent } from './instances/instances.component';

@NgModule({
  imports: [
    CommonModule,
    MonitorRoutingModule,
    ButtonModule,
    DataTableModule,
    CardModule,
  ],
  declarations: [MonitorComponent, HomeComponent, DataFilterPipe, MembersComponent, InstancesComponent],
  exports: [
  ]
})
export class MonitorModule { }
