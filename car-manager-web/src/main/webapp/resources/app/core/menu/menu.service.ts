import { Injectable } from '@angular/core';
import { 
    Headers,
    Http
} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { MenuItem } from './menu-item';

@Injectable()
export class MenuService {

    private fetchMenuListUrl = "service/menu";

    constructor(private http: Http) {}
        
    fetchMenu(): Promise<MenuItem> {
        return this.http.get(this.fetchMenuListUrl)
            .toPromise()
            .then(response => {
                let menu: MenuItem = response.json() as MenuItem
                return menu;
            });
    }

}