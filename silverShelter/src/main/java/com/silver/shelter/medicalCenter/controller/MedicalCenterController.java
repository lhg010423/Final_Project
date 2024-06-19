package com.silver.shelter.medicalCenter.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.model.SurveyForm;
import com.silver.shelter.careGiver.service.CareGiverClustering;
import com.silver.shelter.careGiver.service.KMeansClusteringService;
import com.silver.shelter.medicalCenter.model.dto.Doctor;
import com.silver.shelter.medicalCenter.model.dto.DoctorAppointment;
import com.silver.shelter.medicalCenter.model.service.DoctorService;
import com.silver.shelter.member.model.dto.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("medicalCenter")
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
@Slf4j
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
	
	@ResponseBody
	@PostMapping("reservation/result")
	public String getDrNameByDrNoPost(@RequestBody Map<String, String> requestBody) {
	    String doctorNo = requestBody.get("doctorNo");

	    // 서비스 호출 및 로직 처리
	    String doctorName = doctorService.getdrnamebydrno(doctorNo); // 의사 이름을 서비스에서 가져옴

	    return doctorName; // 의사 이름을 클라이언트로 반환
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
	

    private final KMeansClusteringService clusteringService;
    private final CareGiverClustering cgc = new CareGiverClustering();
    // 클라이언트에서 전달된 데이터를 기반으로 클러스터링 수행하고 결과를 반환
    @PostMapping("careGivers")
    @ResponseBody
    public List<CareGiver> clusterCaregivers(@RequestBody SurveyForm formData) throws IOException{
        // 클라이언트에서 전달된 데이터를 기반으로 클러스터링 수행

        List<CareGiver> caregivers1 = clusteringService.getAllCareGiversFromDatabase();
        
        
        // 클러스터링 실행
        int numClusters = 25;
        List<List<CareGiver>> clusters = cgc.clusterCareGivers(caregivers1, numClusters);

        // 결과 출력
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + (i + 1) + ":");
            for (CareGiver caregiver : clusters.get(i)) {
                System.out.println(caregiver);
            }
            System.out.println();
        }
        
        // 클러스터 중심값 얻기
        List<CareGiver> clusterCentroids = cgc.getClusterCentroids();
        // 클러스터 중심값 활용 예시
        for (int i = 0; i < clusterCentroids.size(); i++) {
            CareGiver centroid = clusterCentroids.get(i);
            System.out.println("Cluster " + i + " centroid: " + centroid);
        }
        
        int num = clusteringService.getMatchingCareGivers(clusterCentroids, formData);
        List<CareGiver> result = clusters.get(num);
        
        // 클러스터링 결과를 클라이언트로 반환
        return result;
    }
	    
    @Autowired
    private com.silver.shelter.careGiver.service.caregiverService caregiverService;

    private static final Logger logger = LoggerFactory.getLogger(MedicalCenterController.class);

    @PostMapping("/selectCaregiver")
    @ResponseBody
    public ResponseEntity<?> selectCaregiver(@RequestParam("caregiverId") int caregiverId,
                                             @SessionAttribute("loginMember") Member loginMember) {
        logger.info("Received request to select caregiver with ID: {}", caregiverId);
        logger.info("Logged in member ID: {}", loginMember.getMemberNo());

        try {
            caregiverService.selectCaregiver(caregiverId, loginMember.getMemberNo());
            logger.info("Updated caregiver ID in MEMBER table");

            CareGiver caregiverInfo = caregiverService.getCaregiverInfoById(caregiverId);
            if (caregiverInfo == null) {
                logger.warn("No caregiver information found for ID: {}", caregiverId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 요양사 정보를 찾을 수 없습니다.");
            }

            logger.info("Retrieved caregiver information: {}", caregiverInfo);
            return ResponseEntity.ok().body(caregiverInfo);
        } catch (Exception e) {
            logger.error("Error occurred while selecting caregiver: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    
    @ResponseBody
    @PostMapping("getReservedDates")
    public ResponseEntity<List<DoctorAppointment>> getReservedDatesPost(@SessionAttribute("loginMember") Member loginMember) {
        List<DoctorAppointment> reservedDates = doctorService.getReservedDates(loginMember.getMemberNo());
        return ResponseEntity.ok().body(reservedDates);
    }

	
	@ResponseBody
	@PostMapping("getReservationsForDate")
	public List<DoctorAppointment> getReservationsForDate(
	    @SessionAttribute("loginMember") Member loginMember,
	    @RequestBody String clubResvTime) {
	    
	    log.info("현재 클릭된 시간은? == {} ", clubResvTime);
	    
	    // Create a DoctorAppointment object with the received clubResvTime and memberNo from session
	    DoctorAppointment reservation = DoctorAppointment.builder()
	            .drApptTime(clubResvTime)
	            .memberNo(loginMember.getMemberNo())
	            .build();
	    
	    // Call a service method to retrieve reservations for the specified date/time
	    List<DoctorAppointment> reservations = doctorService.getReservationsForDate(reservation);
	    for (DoctorAppointment appointment : reservations) {
	        log.info("Appointment details: doctorNo={}, drApptTime={}", appointment.getDoctorNo(), appointment.getDrApptTime());
	    }

	    // Return the list of reservations as JSON response
	    return reservations;
	}

	@GetMapping("reservationCheck/update/{date:[0-9]+}")
	public String updateReserv(@SessionAttribute("loginMember")Member loginMember,
								Model model,
								@PathVariable("date") String date,
						
								RedirectAttributes ra) {
		
		 String formattedDate = String.join("-", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
		
		 DoctorAppointment reservation = DoctorAppointment.builder()
					.drApptTime(formattedDate)
					.memberNo(loginMember.getMemberNo())
					.build();
		 
		 log.info("여기에 뭐가 들어오는지 보여줘 == {}",formattedDate);
		List<DoctorAppointment> reservationList = doctorService.getReservationsForDate(reservation);
		
		String path = null; 
		String message = null;
		
		if(reservationList.isEmpty()) {
			
			message = "예약한 날짜가 없습니다.";
			path = "redirect:/medicalCenter/medicalCenterMain";
			
			ra.addFlashAttribute("message",message);
			
		} else {
			
			path = "medicalCenter/updateReserv";
			
			model.addAttribute("reservationList",reservationList);
			
		}
		
		return path;
	}
	
	@PostMapping("reservationCheck/myReserUpdate/{date:[0-9]+}")
	public String myReserUpdate(@SessionAttribute("loginMember")Member loginMember,
			@PathVariable("date") String date,
			@RequestParam("clubResvTime") String clubResvTime,
			RedirectAttributes ra) {
		
		 String formattedDate = String.join("-", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
		
		 formattedDate += " "+clubResvTime;
		 
		 
		 DoctorAppointment clubresv = DoctorAppointment.builder()
				 					.drApptTime(formattedDate+=clubResvTime)
				 					.memberNo(loginMember.getMemberNo())
				 					.build();
				 					

		
		log.info("뭐가 찍히려나 == {}",clubresv);
		
		String path = null;

		




		
		return path;
	}
	
}
	


