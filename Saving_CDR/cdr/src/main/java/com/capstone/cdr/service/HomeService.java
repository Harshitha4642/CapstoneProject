package com.capstone.cdr.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.cdr.dto.CDRForm;
import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.entity.CDR;
import com.capstone.cdr.entity.Customer;
import com.capstone.cdr.entity.Location;
import com.capstone.cdr.entity.Type;
import com.capstone.cdr.repository.CDRRepository;
import com.capstone.cdr.repository.CustomerRepository;
import com.capstone.cdr.repository.LocationRepository;
import com.capstone.cdr.repository.TypeRepository;

@Service
public class HomeService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CDRRepository cdrRepository;

	public List<Location> getLocations() {
		List<Location> locations = (List<Location>) locationRepository.findAll();
		return locations;
	}

	public List<Type> getTypes() {
		List<Type> types = (List<Type>) typeRepository.findAll();
		return types;
	}
	

	public ExcecutionStatus saveCDRService(CDRForm form) {
	    ExcecutionStatus status = new ExcecutionStatus();
	    CDR cdr = new CDR();
	    Type type = typeRepository.findByCallType(form.getCallType());
	    Optional<Customer> subscriber = customerRepository.findById(form.getSubscriber());
//	    
//	    System.out.println(form.getCallType());
//	    System.out.println(form.getDuration());
//	    System.out.println(form.getRecieverLocation().toString());
//	    System.out.println(form.getSubscriber());
//	    System.out.println(form.getReciever());
//	   
	    Optional<Customer> reciever = customerRepository.findById(form.getReciever());
	    if(subscriber.isEmpty() || reciever.isEmpty())
	    {
	    	System.out.println("No subscriber exist");
	    	status.setStatus("no user exist");
	    	return status;
	    }
	    Optional<Location> subscriberLocation = locationRepository.findByLocationName(form.getSubscriberLocation());
	    Optional<Location> recieverLocation = locationRepository.findByLocationName(form.getRecieverLocation());
	
//	    Date date = form.getDate();
//	    System.out.println(date.toString());
//	    Instant instant = date.toInstant();
//        ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
//        LocalDate localDate = zone.toLocalDate();
			
//			String dateString = form.getDate(); // Assuming dateString is the String representation of the date
//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern as needed
//	

	   String date = form.getDate();
String time = form.getTime();
DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

try {
    LocalDate localDate = LocalDate.parse(date, dateFormatter);
    LocalTime localTime = LocalTime.parse(time, timeFormatter);

    LocalDateTime localDateTime = localDate.atTime(localTime);

    cdr.setDuration(form.getDuration());
    cdr.setCallType(type);
    cdr.setSubscriberLocation(subscriberLocation.get());
    cdr.setRecieverLocation(recieverLocation.get());
    cdr.setSubscriber(subscriber.get());
    cdr.setReciever(reciever.get());
    //cdr.setDateTime(localDateTime); // Assuming your CDR class has a field for both date and time
    cdr.setDate(localDate);
    cdr.setTime(localTime);

    cdrRepository.save(cdr);

    status.setStatus("added successfully");
} catch (DateTimeParseException e) {
    // Handle parsing error
    e.printStackTrace();
    status.setStatus("Error parsing date or time");
}
status.setStatus("errors");
return status;
	}
	}
