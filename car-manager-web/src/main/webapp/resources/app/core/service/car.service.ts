import { Injectable } from '@angular/core';
import { 
    Headers,
    Http
 } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { 
    Car,
    Result
} from '../../shared/common/common';

@Injectable()
export class CarService {

    private submitCarUrl = "service/car/submit";
    private fetchCarListUrl = "service/car/list";

    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {}
        
    fetchCarList(): Promise<Car[]> {
        return this.http.get(this.fetchCarListUrl)
            .toPromise()
            .then(response => {
                let carList: Car[] = response.json() as Car[]
                return carList;
            });
    }

    submitCar(car: Car): Promise<Result<Car>> {
        return this.http
            .post(this.submitCarUrl, JSON.stringify(car), {headers: this.headers})
            .toPromise()
            .then(response => response.json() as Result<Car>);
    }

}