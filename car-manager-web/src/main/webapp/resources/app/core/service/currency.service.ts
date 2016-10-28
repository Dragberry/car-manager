import { Injectable } from '@angular/core';
import { 
    Headers,
    Http
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class CurrencyService {

    private fetchCurrencyListUrl = "service/currency/list";

    constructor(private http: Http) {}

    fetchCurrencyList(): Promise<string[]> {
        return this.http.get(this.fetchCurrencyListUrl)
            .toPromise()
            .then(response => {
                let currencyList: string[] = response.json() as string[];
                return currencyList;
            });
    }
}