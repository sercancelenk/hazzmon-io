import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {MembersComponent} from './members/members.component';
import {InstancesComponent} from './instances/instances.component';
import {MonitorModule} from './monitor.module';
import {MonitorComponent} from './monitor.component';

const routes: Routes = [
  {
    path: '',
    component: MonitorComponent,
    children: [
      {
        path: 'home',
        component: HomeComponent},
      {
        path: 'members',
        component: MembersComponent
      },
      {
        path: 'instances',
        component: InstancesComponent
      },
      {
        path: 'key',
        loadChildren: './key/key.module#KeyModule',
      },
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
      }],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MonitorRoutingModule {
}
