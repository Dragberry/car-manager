import { NgModule }             from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './component/dashboard/dashboard.component';

export const routes: Routes = [
    { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'transaction', loadChildren: 'app/transactions/transactions.module#TransactionsModule' },
    { path: 'car', loadChildren: 'app/cars/cars.module#CarsModule' },
    { path: 'admin', loadChildren: 'app/admin/admin.module#AdminModule' }

];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}