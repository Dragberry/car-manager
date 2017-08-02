import { Injectable } from '@angular/core';
import { 
    Headers,
    Http
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class CurrencyService {

    private fetchCurrencyListUrl = "service/currency/list";
    private refreshCurrenciesUrl = "service/currency/refresh";
  
    constructor(private http: Http) {}

    fetchCurrencyList(): Promise<string[]> {
        return this.http.get(this.fetchCurrencyListUrl)
            .toPromise()
            .then(response => {
                let currencyList: string[] = response.json() as string[];
                return currencyList;
            });
    }
  
  refreshCurrencies(): Promise<boolean> {
    return this.http.get(this.refreshCurrenciesUrl)
            .toPromise()
            .then(response => {
                let result: boolean = response.json() as boolean;
                return result;
            });
  }
}