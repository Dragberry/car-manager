import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { TransactionType } from '../../shared/common/transaction-type';

@Injectable()
export class TransactionTypeService {

    private fetchTransactionTypeListUrl = "service/transaction/type/list";

    constructor(private http: Http) {}
        
    fetchTransactionTypeList(customerKey: number): Promise<TransactionType[]> {
        return this.http.get(this.fetchTransactionTypeListUrl)
            .toPromise()
            .then(response => {
                let transactionTypeList: TransactionType[] = response.json() as TransactionType[]
                return transactionTypeList;
            });
    }

}