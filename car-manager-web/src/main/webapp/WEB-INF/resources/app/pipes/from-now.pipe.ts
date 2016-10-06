import {PipeTransform, Pipe } from '@angular/core';
import * as moment from 'moment';

@Pipe({name:'fromNow'})
export class FromNowPipe implements PipeTransform {
    transform(value, args) {
        console.debug(`Value ${value}; FromNow ${moment(value).fromNow()}`);
        return moment(value).fromNow();
    }
} 