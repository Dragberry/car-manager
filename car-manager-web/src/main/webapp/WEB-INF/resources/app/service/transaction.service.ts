import { Injectable } from '@angular/core';
import { 
    Headers,
    Http
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Transaction } from '../common/transaction';

@Injectable()
export class TransactionService {

    private fetchTnxListUrl = "service/transaction/list";

    constructor(private http: Http) {}
        
    fetchTransactionList(): Promise<Transaction[]> {
        return this.http.get(this.fetchTnxListUrl)
            .toPromise()
            .then(response => {
                let tnxs: Transaction[] = response.json() as Transaction[];
                return tnxs;
            });
    }
}