import  { Component } from '@angular/core';

@Component({
    selector: 'cm-app',
    template: `
        <cm-menu></cm-menu>
        <cm-transaction-summary></cm-transaction-summary>
        <cm-transaction-list></cm-transaction-list>
        `
})
export class CarManagerAppComponent {

    customerName: string = "Makseemka";

}