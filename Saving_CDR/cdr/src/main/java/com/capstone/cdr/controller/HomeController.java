package com.capstone.cdr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.cdr.dto.CDRForm;
import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.entity.Location;
import com.capstone.cdr.entity.Type;
import com.capstone.cdr.service.HomeService;



@RestController
@CrossOrigin
@RequestMapping("/api/home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@GetMapping("/allLocations")
	public List<Location> getAllLocations()
	{
		List<Location> locations = homeService.getLocations();
	
		return locations;
	}

	@GetMapping("/allTypes")
	public List<Type> getAllTypes()
	{
		List<Type> types = homeService.getTypes();
//		for(Location l: locations)
//			System.out.println(l.getLocationName());
		return types;
	}
	
	@PostMapping("/saveCDR")
	public ExcecutionStatus saveCDR(@RequestBody CDRForm form)
	{
		ExcecutionStatus status = new ExcecutionStatus();
		status = homeService.saveCDRService(form);
		return status;
	}
}
