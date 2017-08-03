import { Component } from '@angular/core';

import { CurrencyService } from '../../core/service/currency.service';

@Component({
    selector: "cm-admin-general",
    templateUrl: './app/admin/component/admin-general.component.html',
    providers: [
        
    ]
})
export class AdminGeneralComponent {
  
  constructor(private currencyService: CurrencyService) {}
  
  isRefreshButtonEnabled(): boolean {
    return false;
  }
  
  refreshCurrencies(): void {
    this.currencyService.refreshCurrencies()
      .then(response => console.log(response));
  }
  
}