package com.silver.shelter.medicalCenter.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.communication.model.service.CommunicationService;
import com.silver.shelter.medicalCenter.model.dto.Doctor;
import com.silver.shelter.medicalCenter.model.dto.DoctorAppointment;
import com.silver.shelter.medicalCenter.model.service.DoctorService;
import com.silver.shelter.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("medicalCenter")
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
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
    public String dateChoice(@RequestParam("resDoctorName") String resDoctorName, Model model) {
        // 서비스 호출 및 로직 처리
        List<Date> dates = doctorService.getDateByDoctorName(resDoctorName);

        // 입력 및 출력 형식 지정
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy년MM월dd일HH:mm", Locale.KOREA);

        // 날짜를 원하는 형식으로 변환
        List<String> formattedDates = dates.stream()
                .map(date -> outputFormat.format(date))
                .collect(Collectors.toList());

        // 모델에 변환된 날짜 추가
        model.addAttribute("dates", formattedDates);

        return "medicalCenter/dateResult";
    }
	

	/** 게스트룸 예약 하기 
	 * @param paramMap
	 * @param loginMember
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("reservation/doctorReservation")
	public int reservation(@RequestParam("resDoctorName") String resDoctorName,
							@RequestParam("drApptTime") String drApptTime,
							@RequestBody Map<String, Object> paramMap,
						   @SessionAttribute("loginMember")Member loginMember) {
		
		
		// 로그인한 회원의 번호 가져오기
	    loginMember.getMemberNo();
	    
	    // ClubReservation 객체 생성 및 데이터 설정
	    DoctorAppointment reservation = new DoctorAppointment();
	    
	    // paramMap에서 clubResvTime 값을 String으로 가져와 설정
	    reservation.setDrApptTime(drApptTime);
	    
	    // 회원 번호 설정
	    reservation.setMemberNo(loginMember.getMemberNo());
	    
	    int docNo = doctorService.getNoByDoctorName(resDoctorName);
	    reservation.setDoctorNo(docNo);
	    
	    int result = doctorService.doctorReservation(reservation);
		    
			
			if(result > 0) {
					
				return result; 
			
			} else {
			
				return result;
			}
			
	    }
	}
	


