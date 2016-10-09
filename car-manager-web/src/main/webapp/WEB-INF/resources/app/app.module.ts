import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { ROUTES } from './app.routes';

import { CarManagerAppComponent } from './app.component'; 
import { MenuComponent } from './component/menu/menu.component';
import { TransactionCreateComponent } from './transactions/transaction-create.component';
import { TransactionListComponent } from './transactions/transaction-list.component';
import { TransactionSummaryComponent } from './transactions/transaction-summary.component';

import { FromNowPipe } from './pipes/from-now.pipe';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        RouterModule.forRoot(ROUTES)
        ],
    declarations: [
        CarManagerAppComponent,
        MenuComponent,
        TransactionCreateComponent,
        TransactionListComponent,
        TransactionSummaryComponent,
        FromNowPipe
        ],
    bootstrap: [CarManagerAppComponent]
})
export class AppModule {

}