import  { Component } from '@angular/core';

@Component({
    selector: 'cm-app',
    template: `
        <h1>Car Manager</h1>
        <h3>Welcome, {{customerName}}!</h3>
        <cm-transaction-summary></cm-transaction-summary>
        <cm-transaction-list></cm-transaction-list>
        `
})
export class CarManagerAppComponent {

    customerName: string = "Makseemka";

}