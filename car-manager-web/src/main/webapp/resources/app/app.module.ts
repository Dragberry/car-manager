import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { CoreModule } from './core/core.module';

import { CarManagerAppComponent } from './app.component'; 
import { DashboardComponent } from './component/dashboard/dashboard.component'; 
import { MenuComponent } from './component/menu/menu.component';

@NgModule({
    imports: [
        BrowserModule,
        CoreModule,
        AppRoutingModule
        ],
    declarations: [
        CarManagerAppComponent,
        DashboardComponent,
        MenuComponent
        ],
    bootstrap: [CarManagerAppComponent]
})
export class AppModule {

}