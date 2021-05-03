package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;


//Task 2

@RestController
public class CompensationController {

	private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);
	
	@Autowired
	private CompensationService compensationService;
	
	@GetMapping("/compensation/{id}")
	public Compensation read(@PathVariable String id) {
		LOG.debug("Compensation read request recevied for [{}]",id);
		
		return compensationService.read(id);
	}
	
	
	@PostMapping("/compensation")
	public Compensation create(@RequestBody Compensation compensation) {
		LOG.debug("Compensation create request received [{}]", compensation);
	
		return compensationService.create(compensation);
		
	}
}
