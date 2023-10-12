package com.capstone.cdr.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

import lombok.Data;

@Data
public class CDRForm {
	private int subscriber;
	private int reciever;
	private String date;
	private String time;
	private int duration;
	private String subscriberLocation;
	private String recieverLocation;
	private String callType;
}
