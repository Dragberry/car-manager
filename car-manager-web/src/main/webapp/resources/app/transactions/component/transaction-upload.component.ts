import { Component } from '@angular/core';
import { FileSelectDirective, FileUploader, Headers } from 'ng2-file-upload';

import { CookieService } from 'angular2-cookie/services/cookies.service';

import { CustomerContext } from '../../core/authentication/customer-context';

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
        private customerContext: CustomerContext) {}

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
        this.uploader.authToken = this.customerContext.authToken;
        this.uploader.uploadAll();
    }
}
