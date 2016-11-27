import { NgModule }     from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }  from '@angular/forms';

import { FileUploadModule } from 'ng2-file-upload';

import { MaskedAmountInput } from './masked-input/masked-amount-input.directive';

@NgModule({
    imports:        [ CommonModule ],
    declarations:   [
        MaskedAmountInput
    ],
    exports:        [
        CommonModule,
        FileUploadModule,
        FormsModule,
        MaskedAmountInput
    ]
})
export class SharedModule {

}