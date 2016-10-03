import { Component, Input } from '@angular/core';

@Component({
    selector: "cm-transaction-summary",
    templateUrl: './app/transactions/transaction-summary.component.html'
})
export class TransactionSummaryComponent {

    totalSpent: number = 1000;
    totalSpentByCustomer: number = 9900;
    totalSpentForFuel: number = 1600;
    currency: string = "USD";
    customerName: string  = "Makseemka";

    refresh(additionalValue: number): void {
        this.totalSpent += additionalValue;
        this.totalSpentByCustomer += additionalValue;
        this.totalSpentForFuel += additionalValue; 
    }
}