import { Component, EventEmitter, OnInit } from '@angular/core';

import { AuthenticationService } from './authentication.service';
import { CustomerContext } from './customer-context';
import { UserDetails } from '../../shared/common/user-details';

@Component({
    selector: "cm-simple-login",
    outputs: ["authenticationSuccess"],
    template: `
        <ul [hidden]="!customerContext.isLogged()" class="nav navbar-nav navbar-right">
            <li><a (click)="logout()" href="#">Logout</a></li>
        </ul>
        <form [hidden]="customerContext.isLogged()" (ngSubmit)="login(loginForm.value)" class="navbar-form navbar-right" role="search" #loginForm="ngForm">
            <div class="form-group">
                <input [(ngModel)]="loginData.username" name="username" placeholder="Username" type="text" class="form-control"/>
            </div>
            <div class="form-group">
                <input [(ngModel)]="loginData.password" name="password" placeholder="Password" type="password" class="form-control"/>
            </div>
            <button type="submit" class="btn btn-default">Login</button>
        </form>
        <p [hidden]="!customerContext.isLogged()" class="navbar-text navbar-right">Welcome, {{customerContext.customerName}}!</p>

    `
})
export class SimpleLoginComponent implements OnInit {

    loginData: LoginData = new LoginData();
    authenticationSuccess: EventEmitter<UserDetails> = new EventEmitter<UserDetails>();

    constructor(
        private authenticationService: AuthenticationService,
        private customerContext: CustomerContext) {}

        ngOnInit(): void {
            this.authenticationService.getLoggedUser()
            .then((userDetails: UserDetails) =>{
                if (userDetails) {
                    this.customerContext.customerKey =  userDetails.customerKey;
                    this.customerContext.customerName = userDetails.username;
                    this.authenticationSuccess.emit(userDetails);
                }
            });
            console.log("SimpleLoginComponent onInit");
        }

    login(loginData: LoginData): void {
        this.authenticationService
            .authenticate(loginData.username, loginData.password)
            .then((userDetails: UserDetails) => {
                this.customerContext.customerKey =  userDetails.customerKey;
                this.customerContext.customerName = userDetails.username;
                this.authenticationSuccess.emit(userDetails);
                console.log("Login success!")
            });
    }

    logout(): void {
        this.authenticationService
            .logout()
            .then(() => {
                this.customerContext.customerKey =  2;
                this.customerContext.customerName = "Anonymous";
                console.log("Logout success!")
            });
    }
}

export class LoginData {
    username: string;
    password: string;
}