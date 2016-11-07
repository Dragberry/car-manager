import { Injectable } from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';

import { CookieService } from 'angular2-cookie/services/cookies.service';
import 'rxjs/add/operator/toPromise';

import { UserDetails } from '../../shared/common/user-details';

@Injectable()
export class AuthenticationService {

    private authUrl: string = "auth/login";
    private logoutUrl: string = "auth/logout";
    private loggedUserUrl: string = "auth/logged-user";


    constructor(
        private http: Http,
        private cookieService: CookieService) {}

    authenticate(customerName: string, password: String): Promise<UserDetails> {
        let token: string = btoa(`${customerName}:${password}`);
       
        let headers: Headers = new Headers();
        headers.append('Authorization', `Basic ${token}`);
        headers.append('Content-Type', 'application/json');
        headers.append('Accept', 'application/json');

        let options = new RequestOptions({ headers: headers });

        return this.http.get(this.authUrl, options)
            .toPromise()
            .then(response => {
                return response.json() as UserDetails;
            })
            .catch((response: any) => console.log(`Login Failure: data=${response}`));
    }

    logout(): Promise<void> {
        return this.http.get(this.logoutUrl).toPromise()
            .then(response => {
                return;
            })
            .catch((response: any) => console.log(`Login Failure: data=${response}`));
    }

    getLoggedUser(): Promise<UserDetails> {
     return this.http.get(this.loggedUserUrl)
            .toPromise()
            .then(response => {
                return response.json() as UserDetails;
            })
            .catch((response: any) => console.log("Failure: get logged user!"));
    }
}