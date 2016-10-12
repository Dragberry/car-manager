import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { ROUTES } from './app.routes';

import { CarManagerAppComponent } from './app.component'; 
import { DashboardComponent } from './component/dashboard/dashboard.component'; 
import { MenuComponent } from './component/menu/menu.component';
import { SimpleLoginComponent } from './component/login/simple-login.component';
import { TransactionCreateComponent } from './component/transactions/transaction-create.component';
import { TransactionListComponent } from './component/transactions/transaction-list.component';
import { TransactionSummaryComponent } from './component/transactions/transaction-summary.component';

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
        DashboardComponent,
        MenuComponent,
        SimpleLoginComponent,
        TransactionCreateComponent,
        TransactionListComponent,
        TransactionSummaryComponent,
        FromNowPipe
        ],
    bootstrap: [CarManagerAppComponent]
})
export class AppModule {

}