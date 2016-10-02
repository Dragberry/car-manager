import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { CarManagerAppComponent } from './app.component'; 
import { TransactionListComponent } from './transaction-list.component';

@NgModule({
    imports: [BrowserModule],
    declarations: [
        CarManagerAppComponent,
        TransactionListComponent
        ],
    bootstrap: [CarManagerAppComponent]
})
export class AppModule {

}