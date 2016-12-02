export class Fuel {
    quantity: string;
    type: string;
    cost: string;
}

export class Issue {
    message: string;
}

export class Result<T> {
    object: T;
    issues: Issue[];

    hasIssues = (): boolean => {
        return this.issues && this.issues.length > 0;
    }
}

export class Transaction {
    transactionKey: number;
    executionDate: string;
    description: string;
    amount: string;
    currency: string;
    exchangeRate: string;
    customerKey: number;
    carKey: number;    
    transactionTypeKey: number;
    transactionTypeName: string;
    fuel: Fuel = new Fuel();
    creditorKey: number;
}

export class TransactionSummary {
	amountPerMounth: number;
    totalAmount : number;
	totalAmountByCustomer: number;
	totalFuelAmount: number;
	displayCurrency: string;
}