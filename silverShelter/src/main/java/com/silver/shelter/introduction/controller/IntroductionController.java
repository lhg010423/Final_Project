package com.silver.shelter.introduction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("introduction")
public class IntroductionController {
	
	
	@GetMapping("introduction") 
	public String introduction() {
		
		return "introduction/introduction";
	}
	
	@GetMapping("moveInGuide")
	public String moveInGuide() {
		
		return "introduction/moveInGuide";
	}

}
