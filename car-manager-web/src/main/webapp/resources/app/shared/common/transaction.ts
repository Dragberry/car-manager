import { Fuel } from './fuel';

export class Transaction {
    transactionKey: number;
    executionDate: string;
    description: string;
    amount: number;
    currency: string;
    exchangeRate: string;
    customerKey: number;
    carKey: number;    
    transactionTypeKey: number;
    transactionTypeName: string;
    fuel: Fuel = new Fuel();
    creditorKey: number;

}