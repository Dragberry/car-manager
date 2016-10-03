import { Injectable } from '@angular/core';

import { Transaction } from '../common/transaction';

const DB: Transaction[] = [
     {
        transactionId: 1000,
        executionDate: new Date("03-SEP-2016"),
        amount: "33.3",
        currency: "BYN",
        description: "30 litters of fuel"
     },
     {
         transactionId: 1001,
         executionDate: new Date("03-SEP-2016"),
         amount: "33.3",
         currency: "BYN",
         description: "30 litters of fuel"
      }
]; 

@Injectable()
export class TransactionService {
        
    fetchTransactionList(): Promise<Transaction[]> {
        return Promise.resolve(DB);
    }
}