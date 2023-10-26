package com.capstone.cdr.service;


import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.cdr.dto.NormalCallForm;
import com.capstone.cdr.dto.CSVCallObject;
import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.entity.CDR;
import com.capstone.cdr.entity.Customer;

import com.capstone.cdr.entity.Type;
import com.capstone.cdr.repository.CDRRepository;
import com.capstone.cdr.repository.CustomerRepository;
import com.capstone.cdr.repository.TypeRepository;

@Service
public class HomeService {
	
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CDRRepository cdrRepository;

	public List<Type> getTypes() {
		return typeRepository.findDifferentTypes();
	}
	

	public ExcecutionStatus saveCDRService(NormalCallForm form, boolean isFailed) {
		
	    ExcecutionStatus status = new ExcecutionStatus();
	    CDR cdr = new CDR();
	    Type type = typeRepository.findByType(form.getCallType());
	    Optional<Customer> subscriber = customerRepository.findById(form.getSubscriber());
	   
	    Optional<Customer> reciever = customerRepository.findById(form.getReciever());
	    String date = form.getDate();
		String time = form.getTime();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		if(subscriber.isEmpty() || reciever.isEmpty())
	    {
	    	status.setStatus("no user exist");
	    	return status;
	    }
		
		try {
		    LocalDate localDate = LocalDate.parse(date, dateFormatter);
		    LocalTime localTime = LocalTime.parse(time, timeFormatter);
		    //LocalDateTime localDateTime = localDate.atTime(localTime);
		    //cdr.setDateTime(localDateTime); 
		    cdr.setDate(localDate);
		    cdr.setTime(localTime);
		} catch (DateTimeParseException e) {
		    e.printStackTrace();
		    status.setStatus("Enter date or time");
		}
	    cdr.setSubscriber(subscriber.get());
	    cdr.setReciever(reciever.get());
	    cdr.setSubscriberLocation(form.getSubscriberLocation());
	    cdr.setRecieverLocation(form.getRecieverLocation());
	    cdr.setCallType(type);
		cdr.setReason(form.getReason());
	    
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
		String path = "/home/harshithams/cdr/src/main/java/com/capstone/cdr/CallCDR.csv";
		exportToCSV(cdrList, path);	
	}

	public void exportToCSV(List<CDR> cdrList, String path) {
    String[] header = {"id", "SubscriberName", "SubscriberPhn", "RecieverName",
            "RecieverPhn", "date", "time", "SubscriberLocation", "RecieverLocation", "type",
            "duration", "isFailed", "HasVoiceMail", "VoiceMailDuration","reason"};

			try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(path), CSVFormat.DEFAULT
            .withHeader(header))) {
			for (CDR cdr : cdrList) {
            Object[] csvData = {
                cdr.getId(),
                cdr.getSubscriber().getName(),
                cdr.getSubscriber().getPhonenumber(),
                cdr.getReciever().getName(),
                cdr.getReciever().getPhonenumber(),
                cdr.getDate(),
                cdr.getTime(),
                cdr.getSubscriberLocation(),
                cdr.getRecieverLocation(),
                cdr.getCallType(),
                cdr.getDuration(),
                cdr.isFailed(),
                cdr.isHasVoicemail(),
                cdr.getVoiceMailDuration(),
				cdr.getReason()
            };
            csvPrinter.printRecord(csvData);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
	}

	public ExcecutionStatus saveCallCDRFromCSVObjects(List<CSVCallObject> csvObjects)
	{
		ExcecutionStatus status = new ExcecutionStatus();
		System.out.println("I am called");
		for(CSVCallObject object: csvObjects)
		{
			CDR cdr = new CDR();
			String sub_name = object.getSubsciberName();
			String sub_number = object.getSubscriberNumber();
			String rec_name = object.getRecieverName();
			String rec_number = object.getRecieverNumber();
			String date = object.getDate();
			String time = object.getTime();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

			Customer subscriber = customerRepository.findByNameAndPhonenumber(sub_name, sub_number);
			Customer reciever  = customerRepository.findByNameAndPhonenumber(rec_name, rec_number);
			if(subscriber == null)
			{
				status.setStatus("subscriber with name "+ sub_name+ " or with number "+sub_number+ "doesn't exist");
				return status;
			}
			if(reciever == null)
			{
				status.setStatus("subscriber with name "+ rec_name+ " or with number "+rec_number+ "doesn't exist");
				return status;
			}

			cdr.setSubscriber(subscriber);
			cdr.setReciever(reciever);
			cdr.setSubscriberLocation(object.getSubscriberLocation());
			cdr.setRecieverLocation(object.getRecieverLocation());
			cdr.setDuration(object.getDuration());
			cdr.setCallType(object.getType());
			cdr.setFailed(object.isFailed());
			cdr.setHasVoicemail(object.isHasVoiceMail());
			cdr.setReason(object.getReason());
			cdr.setVoiceMailDuration(object.getVoiceMailDuration());
			try {
				LocalDate localDate = LocalDate.parse(date, dateFormatter);
				LocalTime localTime = LocalTime.parse(time, timeFormatter);
				//LocalDateTime localDateTime = localDate.atTime(localTime);
				//cdr.setDateTime(localDateTime); 
				cdr.setDate(localDate);
				cdr.setTime(localTime);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
				status.setStatus("improper date or time");
				return status;
			}
			cdrRepository.save(cdr);	
		} 
		status.setStatus("Added all successfully");
		return status;
	}
}
