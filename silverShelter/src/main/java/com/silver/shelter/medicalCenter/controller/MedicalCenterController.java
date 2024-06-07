package com.silver.shelter.medicalCenter.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.silver.shelter.medicalCenter.model.dto.Doctor;
import com.silver.shelter.medicalCenter.model.service.DoctorService;

@Controller
@RequestMapping("medicalCenter")
public class MedicalCenterController {
	
    @Autowired
    private DoctorService doctorService;

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
	
//	@PostMapping("department")
//	public String department(@RequestParam("selectedDepartment") String selectedDepartment, Model model) {
//	    // 선택된 진료과를 처리하는 로직 추가
//	    System.out.println("선택된 진료과: " + selectedDepartment);
//	    
//	    // 예: 세션에 저장하거나 모델에 추가하여 다음 페이지에 전달
//	    model.addAttribute("selectedDepartment", selectedDepartment);
//
//	    // 다음 단계로 리디렉션 (예: 의사 선택 페이지)
//	    return "redirect:/medicalCenter/doctorMatching";
//	}
	
	@GetMapping("reservation/doctorChoice")
	public String doctorChoice(@RequestParam("departmentName") String departmentName, Model model) {
	    // 서비스 호출 및 로직 처리
	    List<Doctor> doctors = doctorService.getDoctorsByMajorName(departmentName);
	    model.addAttribute("doctors", doctors);
	    return "medicalCenter/doctorChoiceResult";
	}
	
	@GetMapping("reservation/date")
	public String dateChoice(@RequestParam("resDoctorName") String resDoctorName,
								Model model) {
	    // 서비스 호출 및 로직 처리
	    List<Date> date = (List<Date>) doctorService.getDateByDoctorName(resDoctorName);
	    model.addAttribute("date", date);
	    return "medicalCenter/dateResult";
	}

}
