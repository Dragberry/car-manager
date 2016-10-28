import {
    ModuleWithProviders,
    NgModule,
    Optional,
    SkipSelf
}                                   from '@angular/core';

import { CommonModule }             from '@angular/common';
import { HttpModule }               from '@angular/http';

import { SharedModule }             from '../shared/shared.module';

import { CarService }               from './service/car.service';
import { CurrencyService }          from './service/currency.service';
import { TransactionService }       from './service/transaction.service';
import { TransactionTypeService }   from './service/transaction-type.service';

@NgModule({
    imports:        [
        HttpModule,
        SharedModule
    ],
    declarations:   [],
    exports:        [],
    providers:      [
        CarService,
        CurrencyService,
        TransactionService,
        TransactionTypeService
    ]
})
export class CoreModule {

}