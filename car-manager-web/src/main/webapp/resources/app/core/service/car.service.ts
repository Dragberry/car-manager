import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Car } from '../../shared/common/car';

@Injectable()
export class CarService {

    private fetchCarListUrl = "service/car/list";

    constructor(private http: Http) {}
        
    fetchCarList(customerKey: number): Promise<Car[]> {
        return this.http.get(this.fetchCarListUrl)
            .toPromise()
            .then(response => {
                let carList: Car[] = response.json() as Car[]
                return carList;
            });
    }

}