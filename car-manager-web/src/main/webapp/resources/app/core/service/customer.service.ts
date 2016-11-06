import { Injectable } from '@angular/core';
import { 
    Headers,
    Http,
    URLSearchParams,
    RequestOptions
} from '@angular/http';

import 'rxjs/add/operator/toPromise'

import { Customer } from '../../shared/common/customer';

@Injectable()
export class CustomerService {

    private fetchPayerListUrl = "service/customer/payer/list";
    private fetchCreditorListUrl = "service/customer/creditor/list";

    constructor(private http: Http) {}

    fetchPayerList(customerKey: number): Promise<Customer[]> {
        const params = new URLSearchParams();
        params.set("customerKey", customerKey.toString());
        const options = new RequestOptions({ search: params });

        return this.http.get(this.fetchPayerListUrl, options)
            .toPromise()
            .then(response => {
                let payerList: Customer[] = response.json() as Customer[]
                return payerList;
            });
    }

    fetchCreditorList(customerKey: number): Promise<Customer[]> {
        const params = new URLSearchParams();
        params.set("customerKey", customerKey.toString());
        const options = new RequestOptions({ search: params });

        return this.http.get(this.fetchCreditorListUrl, options)
            .toPromise()
            .then(response => {
                let creditorList: Customer[] = response.json() as Customer[]
                return creditorList;
            });
    }

}