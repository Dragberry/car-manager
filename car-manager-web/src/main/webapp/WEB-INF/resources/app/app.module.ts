import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';

import { CarManagerAppComponent } from './app.component'; 
import { MenuComponent } from './component/menu/menu.component';
import { TransactionListComponent } from './transactions/transaction-list.component';
import { TransactionSummaryComponent } from './transactions/transaction-summary.component';

import { FromNowPipe } from './pipes/from-now.pipe';

@NgModule({
    imports: [
        BrowserModule,
        HttpModule
        ],
    declarations: [
        CarManagerAppComponent,
        MenuComponent,
        TransactionListComponent,
        TransactionSummaryComponent,
        FromNowPipe
        ],
    bootstrap: [CarManagerAppComponent]
})
export class AppModule {

}