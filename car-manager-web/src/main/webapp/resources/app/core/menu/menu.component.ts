import { 
    Component,
    OnInit
} from '@angular/core';

import { NavigationEnd, Router } from '@angular/router';

import { MenuService } from './menu.service'
import { MenuItem } from './menu-item';

@Component({
    selector: 'cm-menu',
    templateUrl: './app/core/menu/menu.component.html',
    providers: [
        MenuService
    ]
})
export class MenuComponent implements OnInit {

    menu: MenuItem;
    currentUrl: string;

    constructor(
        private menuService : MenuService,
        private router: Router) {}

    isMenuActive(url: string): boolean {
        return this.currentUrl.startsWith(url);
    }

    isSubMenuActive(url: string): boolean {
        return url == this.currentUrl;
    }
    
    ngOnInit(): void {
        this.fetchMenu();
        this.onNavigationChange();
    }

    onNavigationChange(): void {
        this.router.events.subscribe((navigation: any) => {
            if (navigation instanceof NavigationEnd) {
                let nav: NavigationEnd = navigation as NavigationEnd;
                this.currentUrl = nav.url;
            }
        });
    }

    fetchMenu(): void {
        this.menuService.fetchMenu()
            .then(result => {
                this.menu = result;
            });
    }

    reloadMenu(): void {
        this.fetchMenu();
    }

}