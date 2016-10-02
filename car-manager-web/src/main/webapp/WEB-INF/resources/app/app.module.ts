import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { CarManagerAppComponent } from './app.component'; 
import { TransactionListComponent } from './transactions/transaction-list.component';
import { TransactionSummaryComponent } from './transactions/transaction-summary.component';

@NgModule({
    imports: [BrowserModule],
    declarations: [
        CarManagerAppComponent,
        TransactionListComponent,
        TransactionSummaryComponent
        ],
    bootstrap: [CarManagerAppComponent]
})
export class AppModule {

}