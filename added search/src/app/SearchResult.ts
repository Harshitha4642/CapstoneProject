export interface MessageSearchResult{
    subscriberName: string;
    subscriberPhn: string;
    recieverName: string;
    recieverPhn: string;
    date: string;
    time: string;
    subscriberLocation: string;
    recieverLocation: string;
    status: string;
    type: string;
}

export interface CallSearchResult{
    subscriberName: string;
    subscriberPhn: string;
    recieverName: string;
    recieverPhn: string;
    date: string;
    time: string;
    duration: number;
    subscriberLocation: string;
    recieverLocation: string;
    callType: string;
    hasVoiceMail: boolean;
    voiceMailDuration : number;
    reason: string;

}

export interface SearchContent
{
    content: string;
    type: string;
    category: string;

}
