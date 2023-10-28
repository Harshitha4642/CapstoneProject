package com.capstone.cdr.entity;

import java.time.LocalDate;

import java.time.LocalTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CDR {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "subscriber")
	private Customer subscriber;
	
	@ManyToOne
	@JoinColumn(name = "reciever")
	private Customer reciever;
	
	private LocalDate date;
	private LocalTime time;
	
	private String subscriberLocation;
	private String recieverLocation;
	
	@ManyToOne
	@JoinColumn(name = "type")
	private Type callType;
	
	private int duration;
	private String reason;
	
	private boolean Failed;
	private boolean hasVoicemail;
	private int voiceMailDuration;
	

}













