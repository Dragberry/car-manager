import { Injectable } from '@angular/core';
import { 
    Headers,
    Http
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Transaction } from '../common/transaction';
import { TransactionSummary } from '../common/transaction-summary';

@Injectable()
export class TransactionService {

    private fetchTnxListUrl = "service/transaction/list";
    private fetchTnxSummaryUrl = "service/transaction/summary";

    constructor(private http: Http) {}
        
    fetchTransactionList(): Promise<Transaction[]> {
        return this.http.get(this.fetchTnxListUrl)
            .toPromise()
            .then(response => {
                let tnxs: Transaction[] = response.json() as Transaction[];
                return tnxs;
            });
    }

    fetchTransactionSummary(): Promise<TransactionSummary> {
        return this.http.get(this.fetchTnxSummaryUrl)
            .toPromise()
            .then(response => {
                let tnxs: TransactionSummary = response.json() as TransactionSummary;
                return tnxs;
            });
    }
}