import { Injectable } from '@angular/core';
import { 
    Headers,
    Http
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class CurrencyService {

    private fetchCurrencyListUrl = "service/currency/list";
    private refreshExRatesUrl = "service/ex-rate/refresh";
    private refreshExRatesCheckUrl = "service/ex-rate/refresh/check-state";
  
    constructor(private http: Http) {}

    fetchCurrencyList(): Promise<string[]> {
        return this.http.get(this.fetchCurrencyListUrl)
            .toPromise()
            .then(response => {
                let currencyList: string[] = response.json() as string[];
                return currencyList;
            });
    }
  
    refreshExRates(): Promise<boolean> {
      return this.http.get(this.refreshExRatesUrl)
              .toPromise()
              .then(response => response.json() as boolean);
    }
  
    checkRefreshExRatesState(): Promise<boolean> {
      return this.http.get(this.refreshExRatesCheckUrl)
              .toPromise()
              .then(response => response.json() as boolean);
    }
}