import { 
    Component,
    EventEmitter,
    OnInit 
} from '@angular/core';

import {
    Car,
    Transaction,
    TransactionSummary
} from '../../shared/common/common';

import { CarService } from '../../core/service/car.service';
import { CurrencyService } from '../../core/service/currency.service';
import { TransactionService } from '../../core/service/transaction.service';

@Component({
    selector: 'cm-transaction-list',
    templateUrl: './app/transactions/component/transaction-list.component.html',
    providers: [
        CurrencyService,
        TransactionService
    ]
})
export class TransactionListComponent implements OnInit {

    transactionSummary: TransactionSummary;
    transactionList: Transaction[];
    carList: Car[];
    currencyList: string[];
    selectedCar: number;
    displayCurrency: string;

    initialDataLoaded: EventEmitter<{selectedCar: number, displayCurrency: string}> = new EventEmitter();

    constructor(
        private carService: CarService,
        private currencyService: CurrencyService,
        private transactionService: TransactionService) {}

    ngOnInit(): void {
        this.initialDataLoaded.subscribe(() => this.onInitialDataLoad());
        this.fetchCarList();
        this.fetchCurrencyList();
    }

    onCarChange(): void {
        this.doSearch();
    }

    onCurrencyChange(displayCurrency: string): void {
        this.fetchTransactionSummary(displayCurrency);
    }

    doSearch(): void {
        this.fetchTransactionSummary();
        this.fetchTransactionList();
    }

    fetchTransactionList(): void {
        this.transactionService.fetchTransactionList(this.selectedCar)
            .then(result => this.transactionList = result);
    }

    fetchTransactionSummary(currency?: string): void {
        this.transactionService.fetchTransactionSummary(
            this.selectedCar, 
            currency ? currency : this.displayCurrency)
            .then(result => this.transactionSummary = result);
    }

    fetchCarList(): void {
        this.carService.fetchCarList()
            .then(carList => {
                this.carList = carList;
                if (carList[0]) {
                    this.selectedCar = carList[0].carKey;
                    this.emitInitialDataLoadedEvent();
                }
            });
    }

    fetchCurrencyList(): void {
        this.currencyService.fetchCurrencyList()
            .then(currencyList => {
                this.currencyList = currencyList;
                if (currencyList[0]) {
                    this.displayCurrency = currencyList[0];
                    this.emitInitialDataLoadedEvent();
                }
            });
    }

    onInitialDataLoad(): void {
        if (this.selectedCar && this.displayCurrency) {
            this.doSearch();
        }
    }

    emitInitialDataLoadedEvent(): void {
        this.initialDataLoaded.emit({"selectedCar": this.selectedCar, "displayCurrency": this.displayCurrency});
    }

}