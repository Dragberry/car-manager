import { 
    Component,
    OnInit 
} from '@angular/core';

import { Transaction } from '../../shared/common/transaction';
import { TransactionSummary } from '../../shared/common/transaction-summary';

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

    constructor(private transactionService: TransactionService) {}

    ngOnInit(): void {
        this.fetchTransactionSummary();
        this.fetchTransactionList();
    }

    fetchTransactionList(): void {
        this.transactionService.fetchTransactionList()
            .then(result => {
                this.transactionList = result
            });
    }

    fetchTransactionSummary(): void {
        this.transactionService.fetchTransactionSummary()
            .then(result => {
                this.transactionSummary = result
            });
    }

}