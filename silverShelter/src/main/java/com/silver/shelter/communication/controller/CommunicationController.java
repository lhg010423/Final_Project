package com.silver.shelter.communication.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.silver.shelter.member.model.dto.Member;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("communication")

public class CommunicationController {

	@GetMapping("communication")
	public String communication() {
		
		return "communication/communication";
	}
	
	@ResponseBody
	@PostMapping("reservation")
	public int reservation(@RequestBody Map<String, String> map,
						   @SessionAttribute("loginMember")Member loginMember,
						   Model model) {
		
		
		
		return 0;
	}
	
}
