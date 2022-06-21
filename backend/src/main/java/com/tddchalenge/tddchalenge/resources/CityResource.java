package com.tddchalenge.tddchalenge.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/cities")
public class CityResource {
	
	
	@Autowired
	private CityService service;
	
	

}
