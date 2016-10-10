import { Component, OnInit } from '@angular/core';

import { CarService} from '../../service/car.service';
import { CurrencyService} from '../../service/currency.service';

import { Car } from '../../common/car';
import { Transaction } from '../../common/transaction';

@Component({
    selector: "cm-transaction-create",
    templateUrl: "./app/component/transactions/transaction-create.component.html"
})
export class TransactionCreateComponent implements OnInit {

    transaction: Transaction = new Transaction();
    currencyList: string[];
    carList: Car[];

    constructor(
        private currencyService: CurrencyService,
        private carService: CarService
    ) {}

    submitTransaction(transaction: Transaction): void {
        this.transaction = transaction;
        console.log(transaction.carKey);
    }

    ngOnInit(): void {
        this.transaction.executionDate = new Date().toDateString();
        this.fetchCurrencyList();
        this.fetchCarList();
    }

    fetchCurrencyList(): void {
        this.currencyService.fetchCurrencyList()
            .then(currencyList => {
                this.currencyList = currencyList;
                if (currencyList[0]) {
                    this.transaction.currency = currencyList[0];
                }
            });
    }

    fetchCarList(): void {
        this.carService.fetchCarList(3)
            .then(carList => {
                this.carList = carList;
                if (carList[0]) {
                    this.transaction.carKey = carList[0].carKey;
                }
            });
    }

}