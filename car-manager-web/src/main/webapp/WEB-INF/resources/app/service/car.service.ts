import { Injectable } from '@angular/core';
import { 
    Headers,
    Http,
    URLSearchParams,
    RequestOptions
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Car } from '../common/car';

@Injectable()
export class CarService {

    private fetchCarListUrl = "service/car/list";

    constructor(private http: Http) {}
        
    fetchCarList(customerKey: number): Promise<Car[]> {
        const params = new URLSearchParams();
        params.set("customerKey", customerKey.toString());
        const options = new RequestOptions({ search: params });

        return this.http.get(this.fetchCarListUrl, options)
            .toPromise()
            .then(response => {
                let carList: Car[] = response.json() as Car[]
                return carList;
            });
    }

}