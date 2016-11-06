import { Injectable } from '@angular/core';
import { 
    Headers,
    Http
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Transaction } from '../../shared/common/transaction';
import { TransactionSummary } from '../../shared/common/transaction-summary';

@Injectable()
export class TransactionService {

    private fetchTnxListUrl = "service/transaction/list";
    private fetchTnxSummaryUrl = "service/transaction/summary";
    private submitTxnUrl = "service/transaction/submit";

    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {}

    submitTransaction(transaction: Transaction): Promise<Transaction> {
        return this.http
            .post(this.submitTxnUrl, JSON.stringify(transaction), {headers: this.headers})
            .toPromise()
            .then(() => transaction);
    }
        
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