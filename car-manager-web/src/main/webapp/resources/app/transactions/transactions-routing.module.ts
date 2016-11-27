import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TransactionCreateComponent } from './component/transaction-create.component';
import { TransactionListComponent } from './component/transaction-list.component';
import { TransactionUploadComponent } from './component/transaction-upload.component';

@NgModule({
  imports: [RouterModule.forChild([
    { path: 'list', component: TransactionListComponent },
    { path: 'create', component: TransactionCreateComponent },
    { path: 'upload', component: TransactionUploadComponent }
  ])],
  exports: [RouterModule]
})
export class TransactionsRoutingModule {}
