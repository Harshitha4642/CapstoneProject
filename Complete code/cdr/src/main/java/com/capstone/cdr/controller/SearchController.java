package com.capstone.cdr.controller;

import java.time.format.DateTimeParseException;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.cdr.dto.CallSearchResult;
import com.capstone.cdr.dto.MessageSearchResult;
import com.capstone.cdr.dto.SearchContent;
import com.capstone.cdr.service.SearchService;

@CrossOrigin
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;
    
    @PostMapping("/getCallRecords")
    public List<CallSearchResult> getCallRecords(@RequestBody SearchContent searchParameters)
    {
            if(searchParameters.getCategory().equals("customer-name"))
                return searchService.getCallDataByName(searchParameters);
            else if(searchParameters.getCategory().equals("customer-number"))
                return searchService.getCallDataByNumber(searchParameters);
            else if(searchParameters.getCategory().equals("location"))
                return searchService.getCallDataByLocation(searchParameters);
            else if(searchParameters.getCategory().equals("_date"))
                return searchService.getCallDataByDate(searchParameters);
            else
                return null;
                
    };
    

    @PostMapping("/getMessageRecords")
    public List<MessageSearchResult> getMessageRecords(@RequestBody SearchContent searchParameters)
    {
        if(searchParameters.getCategory().equals("customer-name"))
            return searchService.getMessageDataByName(searchParameters);
        else if(searchParameters.getCategory().equals("customer-number"))
            return searchService.getMessageDataByNumber(searchParameters);
        else if(searchParameters.getCategory().equals("location"))
            return searchService.getMessageDataByLocation(searchParameters);
        else if(searchParameters.getCategory().equals("_date"))
            return searchService.getMessageDataByDate(searchParameters);
        else
            return null;
        
    }

    @GetMapping("/getNames")
    public List<String> getAllNames()
    {
        return searchService.getAllNames();
    }

    @GetMapping("/getNumbers")
    public List<String> getAllNumbers()
    {
        return searchService.getAllNumbers();
    }
}
