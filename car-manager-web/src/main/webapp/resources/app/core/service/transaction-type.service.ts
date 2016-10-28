import { Injectable } from '@angular/core';
import { 
    Headers,
    Http,
    URLSearchParams,
    RequestOptions
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { TransactionType } from '../../shared/common/transaction-type';

@Injectable()
export class TransactionTypeService {

    private fetchTransactionTypeListUrl = "service/transaction/type/list";

    constructor(private http: Http) {}
        
    fetchTransactionTypeList(customerKey: number): Promise<TransactionType[]> {
        const params = new URLSearchParams();
        params.set("customerKey", customerKey.toString());
        const options = new RequestOptions({ search: params });

        return this.http.get(this.fetchTransactionTypeListUrl, options)
            .toPromise()
            .then(response => {
                let transactionTypeList: TransactionType[] = response.json() as TransactionType[]
                return transactionTypeList;
            });
    }

}