package com.capstone.cdr.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.dto.MessageForm;
import com.capstone.cdr.entity.Customer;
import com.capstone.cdr.entity.Location;
import com.capstone.cdr.entity.MessageCDR;
import com.capstone.cdr.entity.MessageType;
import com.capstone.cdr.repository.CustomerRepository;
import com.capstone.cdr.repository.LocationRepository;
import com.capstone.cdr.repository.MessageCDRRepository;
import com.capstone.cdr.repository.MessageTypeRepository;

@Service
public class MessageService {
	
	@Autowired
	private MessageTypeRepository messageTypeRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MessageCDRRepository messCDRRepo;
	

	public List<MessageType> getMsgTypes() {
		List<MessageType> types = (List<MessageType>) messageTypeRepository.findAll();
		return types;
	}

	public ExcecutionStatus saveCDRService(MessageForm form) {
	    ExcecutionStatus status = new ExcecutionStatus();
	    MessageCDR cdr = new MessageCDR();
	    MessageType type = messageTypeRepository.findByType(form.getType());
	    Optional<Customer> subscriber = customerRepository.findById(form.getSubscriber());
	    Optional<Customer> reciever = customerRepository.findById(form.getReciever());
	    String sentStatus = form.getStatus();
	   
	    if(subscriber.isEmpty() || reciever.isEmpty())
	    {
	    	status.setStatus("no user exist");
	    	return status;
	    }
	    Optional<Location> subscriberLocation = locationRepository.findByLocationName(form.getSubscriberLocation());
	    Optional<Location> recieverLocation = locationRepository.findByLocationName(form.getRecieverLocation());
	    
	    if(subscriberLocation.isEmpty() || recieverLocation.isEmpty())
	    {
	    	status.setStatus("no location exist");
	    	return status;
	    }

	    String date = form.getDate();
		String time = form.getTime();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		try {
		    LocalDate localDate = LocalDate.parse(date, dateFormatter);
		    LocalTime localTime = LocalTime.parse(time, timeFormatter);
		
		    //LocalDateTime localDateTime = localDate.atTime(localTime);
		
		    cdr.setMessageType(type);
		    cdr.setSubscriberLocation(subscriberLocation.get());
		    cdr.setRecieverLocation(recieverLocation.get());
		    cdr.setSubscriber(subscriber.get());
		    cdr.setReciever(reciever.get());
		    //cdr.setDateTime(localDateTime); // Assuming your CDR class has a field for both date and time
		    cdr.setDate(localDate);
		    cdr.setTime(localTime);
		    cdr.setSentStatus(sentStatus);
		
		    messCDRRepo.save(cdr);
		    status.setStatus("added successfully");
		   
		} catch (DateTimeParseException e) {
		    e.printStackTrace();
		    status.setStatus("Error parsing date or time");
		}
		return status;
	}
	
}
