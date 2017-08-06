import { Component, OnDestroy, OnInit } from '@angular/core';

import { CurrencyService } from '../../core/service/currency.service';

const TIMER_WAIT: number = 10000;
const TIMER_PROCESSING: number = 3000;

@Component({
    selector: "cm-admin-general",
    templateUrl: './app/admin/component/admin-general.component.html',
    providers: [
        
    ]
})
export class AdminGeneralComponent implements OnInit, OnDestroy {
  
  private refreshExRatesState: boolean; 
  private refreshExRatesStateTimer: any;
  private exRatesRefreshingMessage: string;
  
  private refreshExRatesStateTimerValue: number;
   
  constructor(private currencyService: CurrencyService) {}
  
  ngOnInit(): void {
    this.refreshExRatesState = false;
    this.refreshExRatesStateTimer = TIMER_WAIT;
    this.checkRefreshExRatesState(true);
  }

  ngOnDestroy(): void {
    console.log("Clearing timer...");
    window.clearInterval(this.refreshExRatesStateTimer);
  }
  
  setIntervalForCheckingState(stateChanged: boolean): void {
    if (stateChanged) {
      let component: AdminGeneralComponent = this;
      window.clearInterval(this.refreshExRatesStateTimer);
      this.refreshExRatesStateTimer = setInterval(() => {
        console.log("timer: " + this.refreshExRatesStateTimerValue);
        component.checkRefreshExRatesState();
      }, this.refreshExRatesStateTimerValue);
    }
  }
  
  checkRefreshExRatesState(firstTime: boolean = false): void {
    this.currencyService.checkRefreshExRatesState()
      .then((state: boolean) => { 
        let stateChanged: boolean = this.refreshExRatesState != state;
        this.refreshExRatesState = state;
        this.refreshExRatesStateTimerValue = state ? TIMER_PROCESSING : TIMER_WAIT;
        this.setIntervalForCheckingState(firstTime || stateChanged);
        this.updateMessage();
      });
  }
  
  refreshExRates(): void {
    if (!this.refreshExRatesState) {
      this.currencyService.refreshExRates()
      .then((state: boolean) => {
        this.refreshExRatesState = state;
        this.refreshExRatesStateTimerValue = state ? TIMER_PROCESSING : TIMER_WAIT;
        this.setIntervalForCheckingState(state);
        this.updateMessage();
      });
    }
  }

  updateMessage(): void {
    this.exRatesRefreshingMessage = this.refreshExRatesState ?
      "Refreshing currencies..." : "";
  }
  
}

