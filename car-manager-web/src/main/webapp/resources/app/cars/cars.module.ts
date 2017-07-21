import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';

import { CarsRoutingModule } from './cars-routing.module';

import { CarListComponent } from './component/car-list.component';
import { CarCreateComponent } from './component/car-create.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        CarsRoutingModule,
        SharedModule
    ],
    declarations: [
        CarListComponent,
        CarCreateComponent
    ],
    exports: [
        CarListComponent,
        CarCreateComponent
    ],
    providers: [

    ]
})
export class CarsModule {

}