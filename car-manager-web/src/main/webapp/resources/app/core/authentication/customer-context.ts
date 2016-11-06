import { Injectable } from '@angular/core';
import { RequestOptions, URLSearchParams } from '@angular/http';

@Injectable()
export class CustomerContext {
    customerName: string = "Anonymous";
    customerKey: number = 2;
    
    /**
     * Checks whether the user is logged or not
     */
    isLogged(): boolean {
        return this.customerKey && this.customerKey != 2;
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