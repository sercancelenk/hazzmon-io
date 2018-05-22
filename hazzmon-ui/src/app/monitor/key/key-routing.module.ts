import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {KeyComponent} from './key.component';
import {ListComponent} from './list/list.component';
import {EvictionComponent} from './eviction/eviction.component';
import {DetailComponent} from './detail/detail.component';

const routes: Routes = [
  {
    path: '',
    component: KeyComponent,
    children: [
      {
        path: 'list',
        component: ListComponent,
      },
      {
        path: 'detail/:keyName',
        component: DetailComponent,
      },
      {
        path: 'eviction',
        component: EvictionComponent,
      },
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full',
      }],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KeyRoutingModule { }
