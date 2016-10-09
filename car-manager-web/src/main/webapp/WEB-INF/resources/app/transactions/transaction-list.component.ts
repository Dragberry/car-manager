import { 
    Component,
    OnInit 
} from '@angular/core';

import { Transaction } from '../common/transaction';
import { TransactionService } from '../service/transaction.service';

@Component({
    selector: 'cm-transaction-list',
    templateUrl: './app/transactions/transaction-list.component.html',
    providers: [
        TransactionService
    ]
})
export class TransactionListComponent implements OnInit {

    transactionList: Transaction[];

    constructor(private transactionService: TransactionService) {}

    ngOnInit(): void {
        this.fetchTransactionList();
    }

    fetchTransactionList(): void {
        this.transactionService.fetchTransactionList()
            .then(result => {
                this.transactionList = result
            });
    }

}