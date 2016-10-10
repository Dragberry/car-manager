import { Route } from '@angular/router'; 

import { DashboardComponent } from './component/dashboard/dashboard.component';
import { TransactionListComponent } from './component/transactions/transaction-list.component';
import { TransactionCreateComponent } from './component/transactions/transaction-create.component';

export  const ROUTES: Route[] = [

    { path: '', component: DashboardComponent },
    { path: 'transaction/list', component: TransactionListComponent },
    { path: 'transaction/create', component: TransactionCreateComponent }

];