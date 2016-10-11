import { Component, OnInit } from '@angular/core';

import { CarService } from '../../service/car.service';
import { CurrencyService } from '../../service/currency.service';
import { TransactionTypeService } from '../../service/transaction-type.service';

import { Car } from '../../common/car';
import { Transaction } from '../../common/transaction';
import { TransactionType } from '../../common/transaction-type';


@Component({
    selector: "cm-transaction-create",
    templateUrl: "./app/component/transactions/transaction-create.component.html"
})
export class TransactionCreateComponent implements OnInit {

    transaction: Transaction = new Transaction();
    currencyList: string[];
    carList: Car[];
    transactionTypeList: TransactionType[];

    customerKey: number = 3;

    constructor(
        private carService: CarService,
        private currencyService: CurrencyService,
        private transactionTypeService: TransactionTypeService
    ) {}

    submitTransaction(transaction: Transaction): void {
        this.transaction = transaction;
        console.log(transaction.carKey);
    }

    ngOnInit(): void {
        this.transaction.executionDate = new Date().toDateString();
        this.fetchCurrencyList();
        this.fetchCarList();
        this.fetchTransactionTypeList();
    }

    fetchTransactionTypeList(): void {
        this.transactionTypeService.fetchTransactionTypeList(this.customerKey)
            .then(transactionTypeList => {
                this.transactionTypeList = transactionTypeList;
                if (transactionTypeList[0]) {
                    this.transaction.transactionTypeKey = transactionTypeList[0].transactionTypeKey;
                }
            });
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
        this.carService.fetchCarList(this.customerKey)
            .then(carList => {
                this.carList = carList;
                if (carList[0]) {
                    this.transaction.carKey = carList[0].carKey;
                }
            });
    }

}