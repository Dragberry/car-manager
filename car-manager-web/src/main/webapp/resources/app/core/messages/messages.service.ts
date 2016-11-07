import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import { Subscriber } from 'rxjs/Subscriber';


import { Message, MessageType } from './message';

@Injectable()
export class MessagesService {

    messages: Message[][];
    source: Observable<Message[]>;
    subscriber: Subscriber<Message[]>;

    constructor() {
        this.source = new Observable((subscriber: Subscriber<Message[]>) => {
            this.subscriber = subscriber;
        });
    }

    addMessage(msg: Message): void {
        this.subscriber.next([msg]);
    }

    addMessages(msgs: Message[]): void {
        this.subscriber.next(msgs);
    }

    reset(): void {
        this.subscriber.next([]);
    }

    showErrorMessage(response: any): void {
        this.subscriber.next([
            new Message(MessageType.ERROR, 
            `An error has occured: ${response.status} (${response.statusText})`, 
            true)
        ]);
    }

}