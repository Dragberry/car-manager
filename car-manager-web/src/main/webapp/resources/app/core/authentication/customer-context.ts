import { Injectable } from '@angular/core';
import { RequestOptions, URLSearchParams } from '@angular/http';

export const GUEST_KEY: number = 1001;
export const GUEST_NAME: string = "Guest";

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
     * Constructs a RequestOptions with customerKey parameter.
     */
    getCustomerRequestOptions(): RequestOptions {
        const params = new URLSearchParams();
        params.set("customerKey", this.customerKey.toString());
        return new RequestOptions({ search: params });
    }
    
}