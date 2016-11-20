import {
    ModuleWithProviders,
    NgModule,
    Optional,
    SkipSelf
}                                   from '@angular/core';

import { CommonModule }             from '@angular/common';
import { HttpModule }               from '@angular/http';

import { CookieService }            from 'angular2-cookie/services/cookies.service';

import { SharedModule }             from '../shared/shared.module';

import { AuthenticationService }    from './authentication/authentication.service';
import { CarService }               from './service/car.service';
import { CurrencyService }          from './service/currency.service';
import { CustomerContext }          from './authentication/customer-context';
import { CustomerService }          from './service/customer.service';
import { MessagesService }          from './messages/messages.service';
import { TransactionService }       from './service/transaction.service';
import { TransactionTypeService }   from './service/transaction-type.service';



@NgModule({
    imports:        [
        HttpModule,
        SharedModule
    ],
    exports:        [],
    providers:      [
        AuthenticationService,
        CarService,
        CookieService,
        CurrencyService,
        CustomerContext,
        CustomerService,
        MessagesService,
        TransactionService,
        TransactionTypeService
    ]
})
export class CoreModule {

}