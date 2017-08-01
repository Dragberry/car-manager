import { Component, OnInit } from '@angular/core';

import { CarService } from '../../core/service/car.service';
import { MessagesService } from '../../core/messages/messages.service';

import { Router } from '@angular/router';

import {
    Car,
    Issue,
    Result
} from '../../shared/common/common';

import { Message, MessageType } from '../../core/messages/message';

@Component({
    selector: "cm-car-create",
    templateUrl: './app/cars/component/car-create.component.html'
})
export class CarCreateComponent implements OnInit {

    car: Car = new Car();

    constructor(
        private carService: CarService,
        private messagesService: MessagesService,
        private router: Router) {}

    ngOnInit(): void {}

    submitCar(): void {
        this.carService.submitCar(this.car)
            .then((result: Result<Car>) => {
                if (!result.issues || result.issues.length == 0) {
                    this.car = result.object;
                    this.messagesService.addMessage(new Message(MessageType.SUCCESS, "Car has been added!", false));
                    this.router.navigate(["car/list"]);
                } else {
                    result.issues.forEach((issue: Issue) => {
                        this.messagesService.addMessage(
                            new Message(MessageType.ERROR, issue.message, true)
                        );
                    })
                }
            })
            .catch((error: any) => {
                this.messagesService.showErrorMessage(error);
            });
    }
}
