import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {KeyRoutingModule} from './key-routing.module';
import {ListComponent} from './list/list.component';
import {KeyComponent} from './key.component';
import {DataTableModule, DropdownModule} from 'primeng/primeng';
import {ButtonModule} from 'primeng/button';
import {CardModule} from 'primeng/card';
import {DetailComponent} from './detail/detail.component';
import {EvictionComponent} from './eviction/eviction.component';
import {FormsModule} from '@angular/forms';
import {ConfirmationPopoverModule} from 'angular-confirmation-popover';


@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        KeyRoutingModule,
        ButtonModule,
        DataTableModule,
        CardModule,
        DropdownModule,
        ConfirmationPopoverModule.forRoot({
            confirmButtonType: 'danger',
            cancelButtonType: 'dark'
        })
    ],
    declarations: [KeyComponent, ListComponent, DetailComponent, EvictionComponent],

})
export class KeyModule {
}
