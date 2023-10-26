package com.capstone.cdr.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.cdr.dto.CallSearchResult;
import com.capstone.cdr.dto.MessageSearchResult;
import com.capstone.cdr.dto.SearchContent;
import com.capstone.cdr.entity.CDR;
import com.capstone.cdr.entity.Customer;
import com.capstone.cdr.entity.MessageCDR;
import com.capstone.cdr.repository.CDRRepository;
import com.capstone.cdr.repository.CustomerRepository;
import com.capstone.cdr.repository.MessageCDRRepository;

@Service
public class SearchService {

    @Autowired
    private MessageCDRRepository messageCDRRepository;

    @Autowired
    private CDRRepository cdrRepository;

    @Autowired 
    private CustomerRepository customerRepository ;
    
    public List<CallSearchResult> getCallDataByName(SearchContent searchParameters)
    {
        List<CallSearchResult> results = new ArrayList<>();
        String content = searchParameters.getContent();
        Customer customer = customerRepository.findByName(content);
            List<CDR> searchResults = cdrRepository.findAllByCustomerId(customer.getId());
            for(CDR cdr: searchResults)
            {
                results.add(CDRToCSRMapper(cdr));
            }
            return results;
       
    }

    public List<CallSearchResult> getCallDataByNumber(SearchContent searchParameters) {
        List<CallSearchResult> results = new ArrayList<>();
        String content = searchParameters.getContent();
     
        Customer customer = customerRepository.findByPhonenumber(content);
        List<CDR> searchResults = cdrRepository.findAllByCustomerId(customer.getId());

        for(CDR cdr: searchResults)
        {
            results.add(CDRToCSRMapper(cdr));
        }
        return results;   
    }

    public List<CallSearchResult> getCallDataByLocation(SearchContent searchParameters) {
        List<CallSearchResult> results = new ArrayList<>();
        String content = searchParameters.getContent();
        
        List<CDR> searchResults = cdrRepository.findAllByLocation(content);

        for(CDR cdr: searchResults)
        {
            results.add(CDRToCSRMapper(cdr));
        }
        return results;   
    }

    public List<CallSearchResult> getCallDataByDate(SearchContent searchParameters) {
        List<CallSearchResult> results = new ArrayList<>();
        String date = searchParameters.getContent();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
		    LocalDate localDate = LocalDate.parse(date, dateFormatter);
            List<CDR> searchResults = cdrRepository.findAllByDate(localDate);

            for(CDR cdr: searchResults)
            {
                results.add(CDRToCSRMapper(cdr));
            }
        return results;

        }catch(Exception e){
            return null;
        }
        
    }

    public List<String> getAllNames() {
        return customerRepository.findAllNames();
    }

    public List<String> getAllNumbers() {
        return customerRepository.findAllNumbers();
    }

     private CallSearchResult CDRToCSRMapper(CDR cdr)
    {
        CallSearchResult csr = new CallSearchResult();
        csr.setCallType(cdr.getCallType().getType());
        csr.setDate(cdr.getDate().toString());
        csr.setDuration(cdr.getDuration());
        csr.setHasVoiceMail(cdr.isHasVoicemail()? "true":"false");
        csr.setReason(cdr.getReason());
        csr.setRecieverLocation(cdr.getRecieverLocation());
        csr.setSubscriberLocation(cdr.getSubscriberLocation());
        csr.setRecieverName(cdr.getReciever().getName());
        csr.setRecieverPhn(cdr.getReciever().getPhonenumber());
        csr.setSubscriberName(cdr.getSubscriber().getName());
        csr.setSubscriberPhn(cdr.getSubscriber().getPhonenumber());
        csr.setTime(cdr.getTime().toString());
        csr.setVoiceMailDuration(cdr.getVoiceMailDuration());

        return csr;
    }

    private MessageSearchResult CDRToMSRMapper(MessageCDR cdr)
    {
        System.out.println("I am mapper");
        MessageSearchResult msr = new MessageSearchResult();
        msr.setDate(cdr.getDate().toString());
        msr.setRecieverLocation(cdr.getRecieverLocation());
        msr.setSubscriberLocation(cdr.getSubscriberLocation());
        msr.setRecieverName(cdr.getReciever().getName());
        msr.setRecieverPhn(cdr.getReciever().getPhonenumber());
        msr.setSubscriberName(cdr.getSubscriber().getName());
        msr.setSubscriberPhn(cdr.getSubscriber().getPhonenumber());
        msr.setTime(cdr.getTime().toString());
        msr.setType(cdr.getMessageType().getType());
        msr.setStatus(cdr.getSentStatus());
        System.out.println(msr.getType());
        System.out.println(cdr.getMessageType().getType());
         System.out.println(cdr.getMessageType());
        return msr;
    }

    public List<MessageSearchResult> getMessageDataByName(SearchContent searchParameters) {
            List<MessageSearchResult> messageResults = new ArrayList<>();
            String content = searchParameters.getContent();
            Customer customer = customerRepository.findByName(content);
            List<MessageCDR> searchResults = messageCDRRepository.findAllByCustomerId(customer.getId());
            for(MessageCDR cdr: searchResults)
            {
                messageResults.add(CDRToMSRMapper(cdr));
            }
            return messageResults;
        }

    public List<MessageSearchResult> getMessageDataByLocation(SearchContent searchParameters) {
        List<MessageSearchResult> messageResults = new ArrayList<>();
        String content = searchParameters.getContent();
        List<MessageCDR> searchResults = messageCDRRepository.findAllByLocation(content);
        for(MessageCDR cdr: searchResults)
        {
            messageResults.add(CDRToMSRMapper(cdr));
        }
        return messageResults;
        
    }

    public List<MessageSearchResult> getMessageDataByNumber(SearchContent searchParameters) {
        List<MessageSearchResult> messageResults = new ArrayList<>();
        String content = searchParameters.getContent();
        Customer customer = customerRepository.findByPhonenumber(content);
        List<MessageCDR> searchResults = messageCDRRepository.findAllByCustomerId(customer.getId());
        for(MessageCDR cdr: searchResults)
        {
            messageResults.add(CDRToMSRMapper(cdr));
        }
        return messageResults;

    }

    public List<MessageSearchResult> getMessageDataByDate(SearchContent searchParameters) {
        List<MessageSearchResult> messageResults = new ArrayList<>();
        String date = searchParameters.getContent();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
		    LocalDate localDate = LocalDate.parse(date, dateFormatter);
            List<MessageCDR> searchResults = messageCDRRepository.findAllByDate(localDate);

            for(MessageCDR cdr: searchResults)
            {
                messageResults.add(CDRToMSRMapper(cdr));
            }
        return messageResults;

        }catch(Exception e){
            return null;
        }
    }
       
}
