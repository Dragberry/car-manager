import { Component } from '@angular/core';

import { CustomerContext } from './customer-context';

@Component({
    selector: "cm-simple-login",
    template: `
        <ul [hidden]="!customerContext.isLogged()" class="nav navbar-nav navbar-right">
            <li><a href="/">Logout</a></li>
        </ul>
        <form [hidden]="customerContext.isLogged()" (ngSubmit)="login(loginForm.value)" class="navbar-form navbar-right" role="search" #loginForm="ngForm">
            <div class="form-group">
                <input type="text" id="username" name="username" class="form-control" placeholder="customerName"/>
            </div>
            <div class="form-group">
                <input type="password" id="password" name="password" class="form-control" placeholder="password"/>
            </div>
            <button type="submit" class="btn btn-default">Login</button>
        </form>
        <p [hidden]="!customerContext.isLogged()" class="navbar-text navbar-right">Welcome, custommer!</p>

    `
})
export class SimpleLoginComponent {

    constructor(private customerContext: CustomerContext) {}

    login(loginForm: any): void {
        console.log(loginForm);
    }
}