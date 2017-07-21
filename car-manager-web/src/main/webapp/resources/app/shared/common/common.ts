export class Car {
    carKey: number;
    brand: string;
    model: string;
    purchaseDate: string;
    saleDate: string;
}

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
	amountPerMonth: number;
    totalAmount : number;
	totalAmountByCustomer: number;
	totalFuelAmount: number;
    totalFuel: number;
    fuelPerMonth: number;
    fuelCostPerMonth: number;
	displayCurrency: string;
    totalRepairCost: number;
}

export class TransactionType {
    transactionTypeKey: number;
    name: string;
}

export class UploadTransactionResult {
    successfulTransactions: number;
    failedTransactions: number;
}