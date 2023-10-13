package com.capstone.cdr.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class MessageCDR {

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
	
	@ManyToOne
	@JoinColumn(name = "subscriberLocation")
	private Location subscriberLocation;
	
	@ManyToOne
	@JoinColumn(name = "recieverLocation")
	private Location recieverLocation;
	
	@ManyToOne
	@JoinColumn(name = "type")
	private MessageType messageType;
	
	private String sentStatus;
	
}
