import { Time } from "@angular/common";

export interface Form{
    subscriber: number;
    reciever: number;
    date: string;
    time: string;
    duration: number;
    subscriberLocation: string;
    recieverLocation: string;
    callType: string;
}