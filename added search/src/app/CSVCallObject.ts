
import { AppType } from "src/AppType";

export interface CSVCallObject
{
    id: number;
    subscriberName: string;
    subscriberNumber: string;
    recieverName: string;
    recieverNumber: string;
    date: string;
    time: string;
    subscriberLocation: string;
    recieverLocation: string;
    type: AppType;
    duration: number;
    isFailed: boolean;
    hasVoiceMail: boolean;
    voiceMailDuration: number
}

                