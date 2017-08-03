import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminGeneralComponent } from './component/admin-general.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: AdminGeneralComponent }
  ])],
  exports: [RouterModule] 
})
export class AdminRoutingModule {}