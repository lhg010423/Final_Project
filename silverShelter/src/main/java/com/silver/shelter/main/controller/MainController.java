package com.silver.shelter.main.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PropertySource("classpath:/config.properties")
public class MainController {
	
	@Value("${my.public.data.service.key.decode}")
	private String decodeServiceKey;
	
	@RequestMapping("/")
	public String mainPage() {
		return "common/main";
	}
	
	@GetMapping("/getServiceKey")
	@ResponseBody
	public String getServiceKey() {
		
		return decodeServiceKey;
	}

}
