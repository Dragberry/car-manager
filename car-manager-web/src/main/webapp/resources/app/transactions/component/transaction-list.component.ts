import { 
    Component,
    OnInit 
} from '@angular/core';

import { Car } from '../../shared/common/car';
import { Transaction } from '../../shared/common/transaction';
import { TransactionSummary } from '../../shared/common/transaction-summary';

import { CarService } from '../../core/service/car.service';
import { TransactionService } from '../../core/service/transaction.service';

@Component({
    selector: 'cm-transaction-list',
    templateUrl: './app/transactions/component/transaction-list.component.html',
    providers: [
        TransactionService
    ]
})
export class TransactionListComponent implements OnInit {

    transactionSummary: TransactionSummary;
    transactionList: Transaction[];
    carList: Car[];
    selectedCar: number;

    constructor(
        private carService: CarService,
        private transactionService: TransactionService) {}

    ngOnInit(): void {
        this.fetchCarList();
    }

    onCarChange(): void {
        this.doSearch();
    }

    doSearch(): void {
        this.fetchTransactionSummary();
        this.fetchTransactionList();
    }

    fetchTransactionList(): void {
        this.transactionService.fetchTransactionList(this.selectedCar)
            .then(result => {
                this.transactionList = result
            });
    }

    fetchTransactionSummary(): void {
        this.transactionService.fetchTransactionSummary(this.selectedCar)
            .then(result => {
                this.transactionSummary = result
            });
    }

    fetchCarList(): void {
        this.carService.fetchCarList()
            .then(carList => {
                this.carList = carList;
                if (carList[0]) {
                    this.selectedCar = carList[0].carKey;
                    this.doSearch();
                }
            });
    }

}