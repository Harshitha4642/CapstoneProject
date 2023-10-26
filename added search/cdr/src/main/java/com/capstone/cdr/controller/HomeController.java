package com.capstone.cdr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.cdr.dto.NormalCallForm;
import com.capstone.cdr.dto.CSVCallObject;
import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.entity.Type;
import com.capstone.cdr.service.HomeService;

@RestController
@CrossOrigin
@RequestMapping("/api/home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;

	@GetMapping("/allTypes")
	public List<Type> getAllTypes()
	{
		return homeService.getTypes();
	}
	
	@PostMapping("/saveNormalCall")
	public ExcecutionStatus saveNormalCall(@RequestBody NormalCallForm form)
	{
		ExcecutionStatus status = homeService.saveCDRService(form, false);
		return status;
	}
	
	@PostMapping("/saveFailedCall")
	public ExcecutionStatus saveFailedCall(@RequestBody NormalCallForm form)
	{
		ExcecutionStatus status = homeService.saveCDRService(form, true);
		return status;
	}

	@PostMapping("/saveCallCDRFromCSV")
	public ExcecutionStatus saveCallCDRFRomCSV(@RequestBody List<CSVCallObject> csvObject)
	{
		System.out.println("i am controller  "+ csvObject.get(1).getRecieverLocation());
		
		return homeService.saveCallCDRFromCSVObjects(csvObject);
	}
}
