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

import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.dto.MessageForm;
import com.capstone.cdr.entity.Customer;

import com.capstone.cdr.entity.MessageCDR;
import com.capstone.cdr.entity.MessageType;
import com.capstone.cdr.repository.CustomerRepository;
import com.capstone.cdr.repository.MessageCDRRepository;
import com.capstone.cdr.repository.MessageTypeRepository;

@Service
public class MessageService {
	
	@Autowired
	private MessageTypeRepository messageTypeRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MessageCDRRepository messCDRRepo;

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

	    String date = form.getDate();
		String time = form.getTime();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		try {
		    LocalDate localDate = LocalDate.parse(date, dateFormatter);
		    LocalTime localTime = LocalTime.parse(time, timeFormatter);
		
		    cdr.setMessageType(type);
		    cdr.setSubscriberLocation(form.getSubscriberLocation());
		    cdr.setRecieverLocation(form.getRecieverLocation());
		    cdr.setSubscriber(subscriber.get());
		    cdr.setReciever(reciever.get());
		    cdr.setDate(localDate);
		    cdr.setTime(localTime);
		    cdr.setSentStatus(sentStatus);
		
		    messCDRRepo.save(cdr);
		    status.setStatus("added successfully");
		   
		} catch (DateTimeParseException e) {
		    e.printStackTrace();
		    status.setStatus("Enter valid date or time");
		}
		saveCDRtoCSV();
		return status;
	}
	
	private void saveCDRtoCSV() {
		List<MessageCDR> cdrList = (List<MessageCDR>) messCDRRepo.findAll();
		String path = "/home/harshithams/cdr/src/main/java/com/capstone/cdr/MessageCDR.csv";
		exportToCSV(cdrList, path);	
	}

	public void exportToCSV(List<MessageCDR> cdrList, String path) {
		String[] header = {"id", "SubscriberName", "SubscriberPhn", "RecieverName",
            "RecieverPhn", "date", "time", "SubscriberLocation", "RecieverLocation", "type",
            "duration", "isFailed", "HasVoiceMail", "VoiceMailDuration","reason"};

		 try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(path),CSVFormat.DEFAULT
		 .withHeader(header))) {
            for (MessageCDR cdr : cdrList) {
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
                    cdr.getSentStatus(),
                    cdr.getMessageType()
                };
                csvPrinter.printRecord(csvData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
