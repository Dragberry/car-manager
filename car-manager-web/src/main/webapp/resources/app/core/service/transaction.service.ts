import { Injectable } from '@angular/core';
import { 
    Headers,
    Http,
    RequestOptions,
    URLSearchParams
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {
    Issue,
    Result
} from '../../shared/common/common';
import { Transaction } from '../../shared/common/common';
import { TransactionSummary } from '../../shared/common/transaction-summary';

@Injectable()
export class TransactionService {

    private fetchTnxListUrl = "service/transaction/list";
    private fetchTnxSummaryUrl = "service/transaction/summary";
    private submitTxnUrl = "service/transaction/submit";

    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {}

    submitTransaction(transaction: Transaction): Promise<Result<Transaction>> {
        return this.http
            .post(this.submitTxnUrl, JSON.stringify(transaction), {headers: this.headers})
            .toPromise()
            .then(response => {
                return response.json() as Result<Transaction>;
            });
    }
        
    fetchTransactionList(selectedCar: number): Promise<Transaction[]> {
        return this.http.get(this.fetchTnxListUrl, this.getSelectedCarOptions(selectedCar))
            .toPromise()
            .then(response => {
                let tnxs: Transaction[] = response.json() as Transaction[];
                return tnxs;
            });
    }

    fetchTransactionSummary(selectedCar: number): Promise<TransactionSummary> {
        return this.http.get(this.fetchTnxSummaryUrl, this.getSelectedCarOptions(selectedCar))
            .toPromise()
            .then(response => {
                let tnxs: TransactionSummary = response.json() as TransactionSummary;
                return tnxs;
            });
    }

    getSelectedCarOptions(selectedCarKey: number): RequestOptions {
        const params = new URLSearchParams();
        params.set("carKey", selectedCarKey.toString());
        return new RequestOptions({ search: params });
    }
}