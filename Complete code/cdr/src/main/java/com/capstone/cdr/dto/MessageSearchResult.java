package com.capstone.cdr.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageSearchResult {
    String subscriberName;
    String subscriberPhn;
    String recieverName;
    String recieverPhn;
    String date;
    String time;
    String subscriberLocation;
    String recieverLocation;
    String status;
    String type;
    
}
