package com.capstone.cdr.dto;

import com.capstone.cdr.entity.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CSVCallObject {
    int id;
    String subsciberName;
    String subscriberNumber;
    String recieverName;
    String recieverNumber;
    String date;
    String time;
    String subscriberLocation;
    String recieverLocation;
    Type type;
    int duration;
    boolean isFailed;
    boolean hasVoiceMail;
    int voiceMailDuration; 
    String reason;   
}
