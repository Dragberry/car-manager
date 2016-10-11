import  { Component } from '@angular/core';
import  { Title } from '@angular/platform-browser';

import { CarService } from './service/car.service';
import { CurrencyService } from './service/currency.service';
import { TransactionTypeService } from './service/transaction-type.service';

@Component({
    selector: 'cm-app',
    providers: [
        CarService,
        CurrencyService,
        TransactionTypeService
    ],
    viewProviders: [
        Title
    ],
    template: `
        <cm-menu></cm-menu>
        <router-outlet></router-outlet>
        `
})
export class CarManagerAppComponent {

    customerName: string = "Makseemka";

    constructor(private title: Title) {
        title.setTitle("Car Manager Application");
    }

}