import { Injectable } from '@angular/core';
import { RequestOptions, URLSearchParams } from '@angular/http';

export const GUEST_KEY: number = 1001;
export const GUEST_NAME: string = "Guest";

const CUSTOMER_KEY_PARAM = "customerKey";

@Injectable()
export class CustomerContext {
    customerName: string = GUEST_NAME
    customerKey: number = GUEST_KEY;
    authToken: string = null;
    
    /**
     * Checks whether the user is logged or not
     */
    isLogged(): boolean {
        return this.customerKey && this.customerKey != GUEST_KEY;
    }

    /**
     * Constructs a name-value pair for customerKey.
     */
    getCustomerKeyParam(): {name: string, value: any } {
        return { name: CUSTOMER_KEY_PARAM, value: this.customerKey };
    }
    
}