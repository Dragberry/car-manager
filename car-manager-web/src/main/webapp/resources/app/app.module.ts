import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';


import { AppRoutingModule } from './app-routing.module';
import { CoreModule } from './core/core.module';

import { CarManagerAppComponent } from './app.component'; 
import { DashboardComponent } from './component/dashboard/dashboard.component'; 
import { MenuComponent } from './core/menu/menu.component';
import { MessagesComponent } from './core/messages/messages.component';
import { SimpleLoginComponent } from './core/authentication/simple-login.component';

@NgModule({
    imports: [
        BrowserModule,
        CoreModule,
        FormsModule,
        AppRoutingModule
        ],
    declarations: [
        CarManagerAppComponent,
        DashboardComponent,
        MenuComponent,
        MessagesComponent,
        SimpleLoginComponent
        ],
    bootstrap: [CarManagerAppComponent]
})
export class AppModule {

}