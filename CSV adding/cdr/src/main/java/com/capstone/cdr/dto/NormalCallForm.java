package com.capstone.cdr.dto;

import lombok.Data;

@Data
public class NormalCallForm {
	private int subscriber;
	private int reciever;
	private String date;
	private String time;
	private int duration;
	private String subscriberLocation;
	private String recieverLocation;
	private String callType;
	private String reason;
	private boolean hasVoiceMail;
	private int voiceMailDuration;
}
