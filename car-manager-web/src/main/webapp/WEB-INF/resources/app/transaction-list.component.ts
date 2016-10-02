import { Component } from '@angular/core';

@Component({
    selector: 'cm-transaction-list',
    template: `
        <h3>Transaction list</h3>
        <table>
            <tr>
                <th>Id</th>
                <th>Description</th>
                <th>Amount</th>
            </tr>
            <tr>
                <td>001</td>
                <td>Fuel</td>
                <td>33.3 BYN</td>
            </tr>
        </table>
    `
})
export class TransactionListComponent {

}