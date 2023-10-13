package com.capstone.cdr.service;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.cdr.dto.NormalCallForm;
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
		
		List<Type> types = new ArrayList<Type>();
		types = typeRepository.findDifferentTypes();
		return types;
	}
	

	public ExcecutionStatus saveCDRService(NormalCallForm form, boolean isFailed) {
		System.out.println("this is service");
		
	    ExcecutionStatus status = new ExcecutionStatus();
	    CDR cdr = new CDR();
	    Type type = typeRepository.findByType(form.getCallType());
	    Optional<Customer> subscriber = customerRepository.findById(form.getSubscriber());
	    
	    System.out.println(form.getCallType());
	    System.out.println(form.getDuration());
	    System.out.println(form.getRecieverLocation().toString());
	    System.out.println(form.getSubscriber());
	    System.out.println(form.getReciever());
	    System.out.println(form.getReason());
	    System.out.println(form.getVoiceMailDuration());
	    System.out.println(form.isHasVoiceMail());
	    System.out.println(type.toString());
	   
	    Optional<Customer> reciever = customerRepository.findById(form.getReciever());
	    Optional<Location> subscriberLocation = locationRepository.findByLocationName(form.getSubscriberLocation());
	    Optional<Location> recieverLocation = locationRepository.findByLocationName(form.getRecieverLocation());

	    String date = form.getDate();
		String time = form.getTime();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		if(subscriber.isEmpty() || reciever.isEmpty())
	    {
	    	System.out.println("No subscriber exist");
	    	status.setStatus("no user exist");
	    	return status;
	    }
		
		try {
		    LocalDate localDate = LocalDate.parse(date, dateFormatter);
		    LocalTime localTime = LocalTime.parse(time, timeFormatter);
		    //LocalDateTime localDateTime = localDate.atTime(localTime);
		    //cdr.setDateTime(localDateTime); 
		    //Assuming your CDR class has a field for both date and time
		    cdr.setDate(localDate);
		    cdr.setTime(localTime);
		} catch (DateTimeParseException e) {
		    e.printStackTrace();
		    status.setStatus("Error parsing date or time");
		}
	    cdr.setSubscriber(subscriber.get());
	    cdr.setReciever(reciever.get());
	    cdr.setSubscriberLocation(subscriberLocation.get());
	    cdr.setRecieverLocation(recieverLocation.get());
	    cdr.setCallType(type);
	    
	    if(isFailed)
	    {
	    	cdr.setDuration(0);
	    	cdr.setHasVoicemail(form.isHasVoiceMail());
	    	cdr.setVoiceMailDuration(form.getVoiceMailDuration());
	    	cdr.setFailed(true);
	    }
	    else
	    {
	    	cdr.setDuration(form.getDuration());
	    	cdr.setHasVoicemail(false);
	    	cdr.setVoiceMailDuration(0);	
	    	cdr.setFailed(false);
	    }    
	    cdrRepository.save(cdr);
	    status.setStatus("added successfully");
	    saveCDRtoCSV();
		return status;
	}

	private void saveCDRtoCSV() {
		List<CDR> cdrList = (List<CDR>) cdrRepository.findAll();
		String path = "/home/harshithams/cdr/src/main/java/com/capstone/cdr/CDR.csv";
		exportToCSV(cdrList, path);	
	}

	private void exportToCSV(List<CDR> cdrList, String path) {
		 try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(path), CSVFormat.DEFAULT)) {
            for (CDR cdr : cdrList) {
                Object[] csvData = {
                    cdr.getId(),
                    cdr.getSubscriber().getName(),
                    cdr.getReciever().getName(),
                    cdr.getDate(),
                    cdr.getTime(),
                    cdr.getSubscriberLocation().getLocationName(),
                    cdr.getRecieverLocation().getLocationName(),
                    cdr.getCallType().getType(),
                    cdr.getDuration(),
                    cdr.isFailed(),
                    cdr.isHasVoicemail(),
                    cdr.getVoiceMailDuration()
                };
                csvPrinter.printRecord(csvData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		
}



