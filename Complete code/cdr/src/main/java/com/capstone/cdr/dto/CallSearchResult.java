package com.capstone.cdr.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class CallSearchResult {
    String subscriberName;
    String  subscriberPhn;
    String recieverName;
    String recieverPhn;
    String date;
    String time;
    int duration;
    String subscriberLocation;
    String recieverLocation;
    String callType;
    String hasVoiceMail;
    int voiceMailDuration;
    String reason;
    
}

