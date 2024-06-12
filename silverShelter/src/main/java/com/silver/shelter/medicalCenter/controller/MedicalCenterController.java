package com.silver.shelter.medicalCenter.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.service.CareGiverService;
import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.communication.model.service.CommunicationService;
import com.silver.shelter.medicalCenter.model.dto.Doctor;
import com.silver.shelter.medicalCenter.model.dto.DoctorAppointment;
import com.silver.shelter.medicalCenter.model.service.DoctorService;
import com.silver.shelter.member.model.dto.Member;

import lombok.Getter;
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
	


	/**
	 * @param resDoctorName
	 * @param drApptTime
	 * @param paramMap
	 * @param loginMember
	 * @return
	 */
	@ResponseBody
	@PostMapping("reservation/doctorReservation")
	public Map<String, Object> reservation(@RequestParam("resDoctorName") String resDoctorName,
	                                       @RequestParam("drApptTime") String drApptTime,
	                                       @RequestBody Map<String, Object> paramMap,
	                                       @SessionAttribute("loginMember") Member loginMember) {
	    // 로그인한 회원의 번호 가져오기
	    int memberNo = loginMember.getMemberNo();
	    
	    // DoctorAppointment 객체 생성 및 데이터 설정
	    DoctorAppointment reservation = new DoctorAppointment();
	    reservation.setDrApptTime(drApptTime);
	    reservation.setMemberNo(memberNo);
	    
	    int docNo = doctorService.getNoByDoctorName(resDoctorName);
	    reservation.setDoctorNo(docNo);
	    
	    int result = doctorService.doctorReservation(reservation);
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("result", result);
	    
	    String drRoom = doctorService.getDrRoomByDoctorNo(docNo);
	    
	    if (result > 0) {
	        response.put("message", "예약 성공");
	        response.put("redirectUrl", "/medicalCenter/reservationSuccess");  // 클라이언트가 사용할 URL
	        response.put("drRoom", drRoom);
	    } else {
	        response.put("message", "예약 실패");
	        response.put("redirectUrl", "/medicalCenter/reservation/doctorMatching");  // 클라이언트가 사용할 URL
	    }
	    
	    return response;
	}
	
	@GetMapping("reservationSuccess")
	public String reservationSuccess() {
		return "medicalCenter/reservationSuccess";
	}
	
	@PostMapping("careGiversResult")
	public String careGiversResult() {
		return "medicalCenter/careGiversResult";
	}
	

	    @Autowired
	    private CareGiverService careGiverService;
	
//	    @PostMapping("/medicalCenter/careGivers")
//	    public ResponseEntity<String> getCareGiverRecommendation(@RequestBody CareGiver careGiver) {
//	        // 클라이언트로부터 받은 설문조사 데이터를 활용하여 요양사 추천 로직을 수행
//	        String recommendation = careGiverService.getRecommendation(careGiver);
//	        return ResponseEntity.ok(recommendation);
//	    }
}
	


