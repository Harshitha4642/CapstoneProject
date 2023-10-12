package com.capstone.cdr.entity;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
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
	private int duration;
	
	@ManyToOne
	@JoinColumn(name = "subscriberLocation")
	private Location subscriberLocation;
	
	@ManyToOne
	@JoinColumn(name = "recieverLocation")
	private Location recieverLocation;
	
	@ManyToOne
	@JoinColumn(name = "type")
	private Type callType;

}













