package com.silver.shelter.communication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("communication")
public class CommunicationController {

	@GetMapping("communication")
	public String communication() {
		
		return "communication/communication";
	}
}
