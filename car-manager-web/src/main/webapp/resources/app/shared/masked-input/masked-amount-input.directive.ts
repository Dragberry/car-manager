import { 
    Directive,
    ElementRef,
    HostListener, 
    Input,
    OnInit,
    Renderer
} from '@angular/core';

import { 
    ControlValueAccessor
} from '@angular/forms';

const DEFAULT_AMOUNT_MASK: string = ".2";

@Directive({
    host: {
        '(input)': 'onInput($event)',
        '(blur)': '_onTouched()'
    },
    selector: '[masked-amount-input]'
})
export class MaskedAmountInput implements OnInit, ControlValueAccessor {

    private inputElement: HTMLInputElement;

    @Input("mask")
    private mask: string;

    private regExp: string;
    private oldValue: string;

    public amountMask: string = "^\\d+(\\\.\\d{1,2})?$";

    _onTouched = () => {}
    _onChange = (_: any) => {}

    constructor(
        private renderer: Renderer,
        private element: ElementRef) {}

    ngOnInit(): void {
        if (!this.mask) {
            this.mask = DEFAULT_AMOUNT_MASK;
        }
        this.buildRegexp();

        if (this.element.nativeElement.tagName === 'INPUT') {
            this.inputElement = this.element.nativeElement;
        } else {
            this.inputElement = this.element.nativeElement.getElementsByTagName('INPUT')[0];
        }
    }

    buildRegexp(): void {
        let parts: string[] = this.mask.split('.');
        let integer: string;
        if (parts[0] && parts[0].match(/^\d+$/)) {
            integer = `^\\d{1,${parts[0]}}`
        } else {
            integer = "^\\d+";
        }
    }

    onInput($event) {
        let newValue: string = $event.target.value;
        if (newValue.match(this.mask)) {
            this.oldValue = newValue;
            this._onChange($event.target.value);
        } else {
            this.inputElement.value = this.oldValue;
        }
    }

    writeValue(value: any) {
        console.log(`writeValue... mask=${this.mask}`);
    }

    registerOnChange(fn: (value: any) => any): void { 
        this._onChange = fn;
    }

    registerOnTouched(fn: () => any): void {
        this._onTouched = fn;
    }

}
