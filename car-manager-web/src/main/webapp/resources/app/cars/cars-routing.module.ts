import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarListComponent } from './component/car-list.component';
import { CarCreateComponent } from './component/car-create.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: 'list', component: CarListComponent },
        { path: 'create', component: CarCreateComponent }
  ])],
  exports: [RouterModule] 
})
export class CarsRoutingModule {}