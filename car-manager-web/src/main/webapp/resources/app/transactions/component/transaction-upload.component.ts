import { Component } from '@angular/core';
import { FileSelectDirective, FileUploader, Headers } from 'ng2-file-upload';

import { CookieService } from 'angular2-cookie/services/cookies.service';

import { CustomerContext } from '../../core/authentication/customer-context';

import { MessagesService } from '../../core/messages/messages.service';

import { Message, MessageType } from '../../core/messages/message';

import {
    Result,
    UploadTransactionResult
} from '../../shared/common/common';

const UPLOAD_URL: string = "service/transaction/upload";
const FILE_NOT_SELECTED: string = "File is no selected";

@Component({
    selector: "cm-transaction-upload",
    templateUrl: "./app/transactions/component/transaction-upload.component.html",
})
export class TransactionUploadComponent {

    uploader: FileUploader = new FileUploader({url: UPLOAD_URL});
    selectedFileName: string = FILE_NOT_SELECTED;

    constructor(
        private cookiService: CookieService,
        private customerContext: CustomerContext,
        private messagesService: MessagesService) {
            this.uploader.onCompleteItem = function (item, response, status, headers) {
                let result: Result<UploadTransactionResult> = JSON.parse(response);
                messagesService.addMessage(new Message(MessageType.INFO, 
                    `The file has been processed: successful=${result.object.successfulTransactions}; failed=${result.object.failedTransactions}`, true)
                );
            };
        }

    onFileSelected(inputElement: HTMLInputElement): void {
        let file: any = inputElement.files.item(0);
        this.selectedFileName = file ? file.name : FILE_NOT_SELECTED;
    }

    upload(): void {
        this.uploader.setOptions({
            url: UPLOAD_URL, 
            headers: [{
                name: 'X-XSRF-TOKEN', 
                value: this.cookiService.get('XSRF-TOKEN')}]
            });
        this.uploader.uploadAll();
        this.uploader.clearQueue();
    }
}
