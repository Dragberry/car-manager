import  { Component } from '@angular/core';
import  { Title } from '@angular/platform-browser';

@Component({
    selector: 'cm-app',
    providers: [
    ],
    viewProviders: [
        Title
    ],
    template: `
        <cm-menu></cm-menu>
        <cm-messages></cm-messages>
        <router-outlet></router-outlet>
        `
})
export class CarManagerAppComponent {

    constructor(private title: Title) {
        title.setTitle("Car Manager Application");
    }

}