package com.capstone.cdr.dto;

import lombok.Data;

@Data
public class MessageForm {
	private int subscriber;
	private int reciever;
	private String date;
	private String time;
	private String subscriberLocation;
	private String recieverLocation;
	private String status;
	private String type;

}
