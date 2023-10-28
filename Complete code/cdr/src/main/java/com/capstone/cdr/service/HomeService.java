package com.capstone.cdr.service;


import java.io.FileWriter;
import java.io.IOException;
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
}
