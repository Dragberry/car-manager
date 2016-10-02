import { Component, Input } from '@angular/core';

@Component({
    selector: "cm-transaction-summary",
    template: `
        <h3>Transaction summary</h3>
        <table>
            <tr>
                <td>Total spent:</td>
                <td>{{totalSpent}}  {{currency}}</td>
            </tr>
             <tr>
                <td>Total spent by {{customerName}}:</td>
                <td>{{totalSpentByCustomer}}  {{currency}}</td>
            </tr>
              <tr>
                <td>Total spent for fuel:</td>
                <td>{{totalSpentForFuel}}  {{currency}}</td>
            </tr>
        </table>
    `
})
export class TransactionSummaryComponent {

    totalSpent: number = 1000;
    totalSpentByCustomer: number = 9900;
    totalSpentForFuel: number = 1600;
    currency: string = "USD";
    @Input()
    customerName: string;
}