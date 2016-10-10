import { 
    Component,
    OnInit 
} from '@angular/core';

import { TransactionService } from '../../service/transaction.service';
import { TransactionSummary } from '../../common/transaction-summary';

@Component({
    selector: "cm-transaction-summary",
    templateUrl: './app/component/transactions/transaction-summary.component.html',
    providers: [
        TransactionService
    ]
})
export class TransactionSummaryComponent implements OnInit {

    transactionSummary: TransactionSummary;

    constructor(private transactionService : TransactionService) {}

    ngOnInit() {
        this.fetchTransactionSummary();
    }

    fetchTransactionSummary(): void {
        this.transactionService.fetchTransactionSummary()
            .then(result => {
                this.transactionSummary = result
            });
    }
}