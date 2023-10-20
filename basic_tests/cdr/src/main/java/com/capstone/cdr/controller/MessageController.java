package com.capstone.cdr.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.dto.MessageForm;
import com.capstone.cdr.entity.MessageType;
import com.capstone.cdr.service.MessageService;

@RestController
@CrossOrigin
@RequestMapping("api/message")
public class MessageController {
	
	@Autowired
	private MessageService msgService;
	
	@GetMapping("/msgTypes")
	public List<MessageType> getMsgTypes()
	{
		return msgService.getMsgTypes();
	}
	
	
	@PostMapping("/saveMessage")
	public ExcecutionStatus saveMessageCDR(@RequestBody MessageForm form)
	{
		ExcecutionStatus status = msgService.saveCDRService(form);
		return status;
	}
}
