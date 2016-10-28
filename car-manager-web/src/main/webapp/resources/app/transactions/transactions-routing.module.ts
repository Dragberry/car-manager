import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TransactionCreateComponent } from './component/transaction-create.component';
import { TransactionListComponent } from './component/transaction-list.component';

@NgModule({
  imports: [RouterModule.forChild([
    { path: 'list', component: TransactionListComponent },
    { path: 'create', component: TransactionCreateComponent }
  ])],
  exports: [RouterModule]
})
export class TransactionsRoutingModule {}
