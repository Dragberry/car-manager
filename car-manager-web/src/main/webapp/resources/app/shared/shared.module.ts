import { NgModule }     from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }  from '@angular/forms';

import { MaskedAmountInput } from './masked-input/masked-amount-input.directive';

@NgModule({
    imports:        [ CommonModule ],
    declarations:   [
        MaskedAmountInput
    ],
    exports:        [
        CommonModule,
        FormsModule,
        MaskedAmountInput
    ]
})
export class SharedModule {

}