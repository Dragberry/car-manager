import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';

import { AdminRoutingModule } from './admin-routing.module';

import { AdminGeneralComponent } from './component/admin-general.component';

@NgModule({
  imports: [
        CommonModule,
        FormsModule,
        AdminRoutingModule,
        SharedModule
    ],
    declarations: [
        AdminGeneralComponent
    ],
    exports: [
        AdminGeneralComponent
    ],
    providers: [

    ]
})
export class AdminModule {
  
}