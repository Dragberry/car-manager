import { Injectable } from '@angular/core';
import { 
    Headers,
    Http
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { CustomerContext } from '../authentication/customer-context';

import {
    Issue,
    Result,
    Transaction,
    TransactionSummary
} from '../../shared/common/common';

import {
    createOptions
} from '../../shared/utils/parameter-utils';

const SELECTED_CAR_PARAM = "selectedCar";
const DISPLAY_CURRENCY_PARAM = "displayCurrency";

@Injectable()
export class TransactionService {

    private fetchTnxListUrl = "service/transaction/list";
    private fetchTnxSummaryUrl = "service/transaction/summary";
    private submitTxnUrl = "service/transaction/submit";

    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(
        private customerContext: CustomerContext,
        private http: Http) {}

    submitTransaction(transaction: Transaction): Promise<Result<Transaction>> {
        return this.http
            .post(this.submitTxnUrl, JSON.stringify(transaction), {headers: this.headers})
            .toPromise()
            .then(response => response.json() as Result<Transaction>);
    }
        
    fetchTransactionList(selectedCar: number): Promise<Transaction[]> {
        return this.http.get(
            this.fetchTnxListUrl, 
            createOptions([
                this.customerContext.getCustomerKeyParam(),
                { name: SELECTED_CAR_PARAM, value: selectedCar }
            ]))
            .toPromise()
            .then(response => response.json() as Transaction[]);
    }

    fetchTransactionSummary(selectedCar: number, displayCurrency: string): Promise<TransactionSummary> {
        return this.http.get(
            this.fetchTnxSummaryUrl,
            createOptions([
                this.customerContext.getCustomerKeyParam(),
                { name: SELECTED_CAR_PARAM, value: selectedCar },
                { name: DISPLAY_CURRENCY_PARAM, value: displayCurrency }
            ]))
            .toPromise()
            .then(response => response.json() as TransactionSummary);
    }

}