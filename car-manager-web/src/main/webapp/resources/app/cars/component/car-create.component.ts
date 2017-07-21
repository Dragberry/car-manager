import { Component, OnInit } from '@angular/core';

import { CarService } from '../../core/service/car.service';

import {
    Car
} from '../../shared/common/common';

@Component({
    selector: "cm-car-create",
    templateUrl: './app/cars/component/car-create.component.html'
})
export class CarCreateComponent implements OnInit {

    car: Car = new Car();

    constructor(carService: CarService) {}

    ngOnInit(): void {

    }
}