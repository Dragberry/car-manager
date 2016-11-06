export enum MessageType {
    ERROR, WARNING, SUCCESS, INFO
}

export const MESSAGE_TYPES: MessageType[] = [
    MessageType.ERROR,
    MessageType.WARNING,
    MessageType.SUCCESS,
    MessageType.INFO
]

export class Message {
    type: MessageType;
    text: string;
    expired: boolean;

    constructor(type: MessageType, text: string, expired: boolean) {
        this.expired = expired;
        this.text = text;
        this.type = type;
    }

}