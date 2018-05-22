import { ExtraOptions, RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Routes = [
  { path: 'hazzmon-ui', loadChildren: 'app/monitor/monitor.module#MonitorModule' },
  { path: '', redirectTo: 'hazzmon-ui', pathMatch: 'full' },
  { path: '**', redirectTo: 'hazzmon-ui' },
];

const config: ExtraOptions = {
  useHash: false,
};

@NgModule({
  imports: [RouterModule.forRoot(routes, config)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
