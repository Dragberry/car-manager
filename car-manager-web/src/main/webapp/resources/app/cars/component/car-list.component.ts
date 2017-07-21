import { Component } from '@angular/core';

import {
    Car
} from '../../shared/common/common';

import { CarService } from '../../core/service/car.service';

@Component({
    selector: "cm-car-list",
    templateUrl: './app/cars/component/car-list.component.html',
    providers: [
        CarService
    ]
})
export class CarListComponent {

    carList: Car[];

    constructor(private carService: CarService) {}

    ngOnInit(): void {
        this.fetchCars();
    }

    fetchCars(): void {
        this.carService.fetchCarList()
            .then(result => this.carList = result);
    }
}