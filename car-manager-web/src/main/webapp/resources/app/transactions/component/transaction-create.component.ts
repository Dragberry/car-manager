import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';

import { Router } from '@angular/router';

import { CustomerContext } from '../../core/authentication/customer-context';

import { CarService } from '../../core/service/car.service';
import { CurrencyService } from '../../core/service/currency.service';
import { CustomerService } from '../../core/service/customer.service';
import { MessagesService } from '../../core/messages/messages.service';
import { TransactionService } from '../../core/service/transaction.service';
import { TransactionTypeService } from '../../core/service/transaction-type.service';

import {
    Issue, Result
} from '../../shared/common/common';
import { Car } from '../../shared/common/car';
import { Customer } from '../../shared/common/customer';
import { Fuel } from '../../shared/common/fuel';
import { Message, MessageType } from '../../core/messages/message';
import { Transaction } from '../../shared/common/transaction';
import { TransactionType } from '../../shared/common/transaction-type';

const DEFAULT_DESCRIPTION = "Топливо";

@Component({
    selector: "cm-transaction-create",
    templateUrl: "./app/transactions/component/transaction-create.component.html"
})
export class TransactionCreateComponent implements OnInit {

    transaction: Transaction = new Transaction();
    carList: Car[];
    creditorList: Customer[];
    currencyList: string[];
    payerList: Customer[];
    payerMap: Map<number, Customer>;
    transactionTypeList: TransactionType[];

    customerName: string;

    constructor(
        private carService: CarService,
        private currencyService: CurrencyService,
        private customerContext: CustomerContext,
        private customerService: CustomerService,
        private messagesService: MessagesService,
        private router: Router,
        private transactionService: TransactionService,
        private transactionTypeService: TransactionTypeService
    ) {}

    ngOnInit(): void {
        let dateFormatter: DatePipe = new DatePipe("en");
        this.transaction.executionDate = dateFormatter.transform(new Date(), "yyyy-MM-dd");
        this.fetchCarList();
        this.fetchCreditorList();
        this.fetchCurrencyList();
        this.fetchPayerList();
        this.fetchTransactionTypeList();
    }

    amountChanged(event: any): void {
        if (this.transaction.amount && this.transaction.fuel.cost) {
            this.transaction.fuel.quantity = this.transaction.amount / this.transaction.fuel.cost;
        }
        this.transaction.description = this.buildFuelDescription(this.transaction.fuel);
    }

    fuelQuantityChanged(event: any): void {
        if (this.transaction.fuel.cost && this.transaction.fuel.quantity) {
            this.transaction.amount = this.transaction.fuel.cost * this.transaction.fuel.quantity;
            this.transaction.description = this.buildFuelDescription(this.transaction.fuel);
        } else {
            this.transaction.amount = 0.00;
            this.transaction.description = DEFAULT_DESCRIPTION;
        }
    }

    fuelCostChanged(event: any): void {
        if (this.transaction.fuel.cost && this.transaction.fuel.quantity) {
            this.transaction.amount = this.transaction.fuel.cost * this.transaction.fuel.quantity;
            this.transaction.description = this.buildFuelDescription(this.transaction.fuel);
        } else {
            this.transaction.amount = 0.00;
            this.transaction.description = DEFAULT_DESCRIPTION;
        }
    }
    
    fuelTypeChanged(event: any): void {
        if (this.transaction.fuel.type && this.transaction.fuel.quantity) {
            this.transaction.description = this.buildFuelDescription(this.transaction.fuel);
        } else {
            this.transaction.description = DEFAULT_DESCRIPTION;
        }
    }
    
    buildFuelDescription(fuel: Fuel): string {
        const template: string = "40 литров бензина АИ92";
        if (fuel.cost && fuel.quantity && fuel.type) {
            return `${fuel.quantity} литр${this.resolveEnding(fuel.quantity)} топлива <${fuel.type}>`;
        }
        return DEFAULT_DESCRIPTION;
    }

    resolveEnding(quantity: number): string {
        let quantityStr: string = quantity.toString();
        let ending: string = "а";
        if (quantityStr.match(/[\.,]/)) {
            ending = "а";
        } else if (quantityStr.match(/([056789]|(1[1-9]))$/)) {
            ending = "ов";
        } else if (quantityStr.match(/\d*[1]$/)) {
            ending = "";
        }
        return ending;
    }
    
    transactionTypeChanged(typeKey: number): void {
        const loanPaymentKey: number = 2;
        if (typeKey == loanPaymentKey) {
            let customer: Customer = this.payerMap.get(this.customerContext.customerKey);
            this.transaction.customerKey = customer.customerKey;
            this.customerName = `${customer.firstName} ${customer.lastName}`;
        }
    }
    
    isFuelShown(): boolean {
        return this.isFuel();
    }

    isFuel(): boolean {
        const fuelKey: number = 1;
        return fuelKey == this.transaction.transactionTypeKey;
    }
    
    isCreditorShown(): boolean {
        return this.isLoanPayment();
    }

    isLoanPayment(): boolean {
        const fuelKey: number = 2;
        return fuelKey == this.transaction.transactionTypeKey;
    }

    submitTransaction(): void {
        this.messagesService.reset();
        if (this.isLoanPayment()) {
            this.transaction.customerKey = this.customerContext.customerKey;
        } else {
            this.transaction.creditorKey = null;
        }
        this.transactionService.submitTransaction(this.transaction)
            .then((result: Result<Transaction>) => {
                if (!result.issues || result.issues.length == 0) {
                    this.transaction = result.object;
                    this.messagesService.addMessage(new Message(MessageType.SUCCESS, "Transaction has been created!", false));
                    this.router.navigate(["transaction/list"]);
                } else {
                    result.issues.forEach((issue: Issue) => {
                        this.messagesService.addMessage(
                            new Message(MessageType.ERROR, issue.message, true)
                        );
                    });
                }
            })
            .catch((error: any) => {
                this.messagesService.showErrorMessage(error);
            });
    }

    fetchCreditorList(): void {
        this.customerService.fetchCreditorList(this.customerContext.customerKey)
            .then(creditorList => {
                this.creditorList = creditorList;
                if (creditorList[0]) {
                    this.transaction.creditorKey = creditorList[0].customerKey;
                }
            });
    }

    fetchPayerList(): void {
        this.customerService.fetchPayerList(this.customerContext.customerKey)
            .then(payerList => {
                this.payerList = payerList;
                if (payerList[0]) {
                    this.transaction.customerKey = payerList[0].customerKey;
                }
                this.payerMap = new Map<number, Customer>();
                for (let payer of this.payerList) {
                    this.payerMap.set(payer.customerKey, payer);
                }
            });
            
    }

    fetchTransactionTypeList(): void {
        this.transactionTypeService.fetchTransactionTypeList(this.customerContext.customerKey)
            .then(transactionTypeList => {
                this.transactionTypeList = transactionTypeList;
                if (transactionTypeList[0]) {
                    this.transaction.transactionTypeKey = transactionTypeList[0].transactionTypeKey;
                }
            });
    }

    fetchCurrencyList(): void {
        this.currencyService.fetchCurrencyList()
            .then(currencyList => {
                this.currencyList = currencyList;
                if (currencyList[0]) {
                    this.transaction.currency = currencyList[0];
                }
            });
    }

    fetchCarList(): void {
        this.carService.fetchCarList(this.customerContext.customerKey)
            .then(carList => {
                this.carList = carList;
                if (carList[0]) {
                    this.transaction.carKey = carList[0].carKey;
                }
            });
    }

}