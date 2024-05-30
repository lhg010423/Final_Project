package com.silver.shelter.medicalCenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("medicalCenter")
public class MedicalCenterController {

	@GetMapping("medicalCenterMain") 
	public String medicalCenterMain() {
		return "medicalCenter/medicalCenterMain";
	}
	
	@GetMapping("medicalCenterIntro") 
	public String medicalCenterIntro() {
		return "medicalCenter/medicalCenterIntro";
	}
	
	@GetMapping("doctorMatching") 
	public String doctorMatching() {
		return "medicalCenter/doctorMatching";
	}
	
	@GetMapping("careGiverMatching") 
	public String careGiverMatching() {
		return "medicalCenter/careGiverMatching";
	}
	
	@GetMapping("ReservationCheck") 
	public String ReservationCheck() {
		return "medicalCenter/ReservationCheck";
	}
	
	@GetMapping("funeralService") 
	public String funeralService() {
		return "medicalCenter/funeralService";
	}
}
