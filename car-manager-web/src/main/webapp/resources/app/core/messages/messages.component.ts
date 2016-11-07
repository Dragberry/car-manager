import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

import { Message, MessageType, MESSAGE_TYPES } from './message';
import { MessagesService } from '../../core/messages/messages.service';

@Component({
    selector: 'cm-messages',
    template: `
        <div *ngIf="messagesToDisplay && messagesToDisplay.length > 0" class="row">
            <div class="alert alert-dismissible" 
                    [class.alert-danger]="isClassEnabled(msgERROR)"
                    [class.alert-warning]="isClassEnabled(msgWARNING)"
                    [class.alert-success]="isClassEnabled(msgSUCCESS)"
                    [class.alert-info]="isClassEnabled(msgINFO)"
                    role="alert">
                <button (click)="clear(messageTypeToDisplay)" 
                        type="button"
                        class="close"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <ul class="list-group">
                    <li *ngFor="let msg of messagesToDisplay" class="list-group-item">{{msg.text}}</li>
                </ul>
            </div>
        </div>
    `
})
export class MessagesComponent {
    messages: Map<MessageType, Message[]> = new Map<MessageType, Message[]>();
    messagesToDisplay: Message[] = [];
    messageTypeToDisplay: MessageType;

    msgINFO: MessageType = MessageType.INFO;
    msgSUCCESS: MessageType = MessageType.SUCCESS;
    msgERROR: MessageType = MessageType.ERROR;
    msgWARNING: MessageType = MessageType.WARNING;

    constructor(
        private messagesService: MessagesService,
        private router: Router) {
            this.onMessageChange();
            this.onNavigationChange();
    }

    isClassEnabled(messageType: MessageType): boolean {
        return this.messageTypeToDisplay == messageType; 
    }

    onNavigationChange(): void {
        this.router.events.subscribe((navigation: any) => {
            if (navigation instanceof NavigationEnd) {
                this.messages.forEach((msgs: Message[]) => {
                    for (let msg of msgs) {
                        if (msg.expired) {
                            msgs.splice(msgs.indexOf(msg));
                        } else {
                            msg.expired = true;
                        }
                    }
                });
                this.setMessageToDisplay();
            }
        });
    }

    onMessageChange(): void {
        this.messagesService.source.subscribe((msgs: Message[]) => {
                if (!msgs || msgs.length == 0) {
                    MESSAGE_TYPES.forEach((type: MessageType) => {
                        this.messages.set(type, []);
                    });
                } else {
                    msgs.forEach(msg => {
                        let category: Message[] = this.messages.get(msg.type);
                        if (!category) {
                            category = [];
                            this.messages.set(msg.type, category);
                        }
                        category.push(msg);
                    });
                }
                this.setMessageToDisplay();
            });
    }

    setMessageToDisplay(): void {
        for (let type of MESSAGE_TYPES) {
            let msgs: Message[] = this.messages.get(type);
            if (msgs && msgs.length > 0) {
                this.messagesToDisplay = msgs;
                this.messageTypeToDisplay = type;
                return;
            }
        }
        this.messagesToDisplay = [];
        this.messageTypeToDisplay = null;
    }

    clear(type:MessageType): void {
        this.messages.set(type, []);
        this.setMessageToDisplay();
    }

}