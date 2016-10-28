import { Injectable } from '@angular/core';

@Injectable()
export class CustomerContext {
    customerName: string = "Anonymous";
    customerKey: number = 2;
    
    isLogged(): boolean {
        return this.customerKey != 2;
    }
}