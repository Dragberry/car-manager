import { 
    Component,
    OnInit
} from '@angular/core';

import { MenuService } from '../../service/menu.service'
import { MenuItem } from '../../common/menu-item';

@Component({
    selector: 'cm-menu',
    templateUrl: './app/component/menu/menu.component.html',
    providers: [
        MenuService
    ]
})
export class MenuComponent implements OnInit {

    menu: MenuItem;

    constructor(private menuService : MenuService) {}
    
    ngOnInit(): void {
        this.fetchMenu();
    }

    fetchMenu(): void {
        this.menuService.fetchMenu()
            .then(result => {
                this.menu = result;
            });
    }

}