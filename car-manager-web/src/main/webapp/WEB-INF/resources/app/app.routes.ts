import { Route } from '@angular/router'; 

import { TransactionListComponent } from './transactions/transaction-list.component';
import { TransactionCreateComponent } from './transactions/transaction-create.component';

export  const ROUTES: Route[] = [

    { path: '', component: TransactionListComponent },
    { path: 'transaction/list', component: TransactionListComponent },
    { path: 'transaction/create', component: TransactionCreateComponent }

];