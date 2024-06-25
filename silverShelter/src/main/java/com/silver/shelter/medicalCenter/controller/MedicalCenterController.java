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

import com.silver.shelter.admin.model.service.AdminService;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.model.SurveyForm;
import com.silver.shelter.careGiver.service.CareGiverClustering;
import com.silver.shelter.careGiver.service.ClusteringService;
import com.silver.shelter.medicalCenter.model.dto.Doctor;
import com.silver.shelter.medicalCenter.model.dto.DoctorAppointment;
import com.silver.shelter.medicalCenter.model.service.DoctorService;
import com.silver.shelter.member.model.dto.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 의료 센터 기능 관련 작업을 관리하는 컨트롤러 클래스입니다.
 */
@Controller
@RequestMapping("medicalCenter")
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
public class MedicalCenterController {
	
    @Autowired
    private DoctorService doctorService;

	/**
	 * 의료 센터 메인 페이지로 이동합니다.
	 * @return 의료 센터 메인 페이지의 뷰 이름
	 */
	@GetMapping("medicalCenterMain") 
	public String medicalCenterMain() {
		return "medicalCenter/medicalCenterMain";
	}
	
	/**
	 * 의료 센터 소개 페이지로 이동합니다.
	 * @return 의료 센터 소개 페이지의 뷰 이름
	 */
	@GetMapping("medicalCenterIntro") 
	public String medicalCenterIntro() {
		return "medicalCenter/medicalCenterIntro";
	}
	
	/**
	 * 의사 매칭 페이지로 이동합니다.
	 * @return 의사 매칭 페이지의 뷰 이름
	 */
	@GetMapping("doctorMatching") 
	public String doctorMatching() {
		return "medicalCenter/doctorMatching";
	}
	
	/**
	 * 간병인 매칭 페이지로 이동합니다.
	 * @return 간병인 매칭 페이지의 뷰 이름
	 */
	@GetMapping("careGiverMatching") 
	public String careGiverMatching() {
		return "medicalCenter/careGiverMatching";
	}
	
	/**
	 * 예약 확인 페이지로 이동합니다.
	 * @return 예약 확인 페이지의 뷰 이름
	 */
	@GetMapping("ReservationCheck") 
	public String ReservationCheck() {
		return "medicalCenter/ReservationCheck";
	}
	
	/**
	 * 장례 서비스 페이지로 이동합니다.
	 * @return 장례 서비스 페이지의 뷰 이름
	 */
	@GetMapping("funeralService") 
	public String funeralService() {
		return "medicalCenter/funeralService";
	}
	
	/**
	 * 특정 부서의 의사 선택 페이지로 이동합니다.
	 * @param departmentName 선택된 부서명
	 * @param model Model 객체
	 * @return 의사 선택 결과 페이지의 뷰 이름
	 */
	@GetMapping("reservation/doctorChoice")
	public String doctorChoice(@RequestParam("departmentName") String departmentName, Model model) {
	    List<Doctor> doctors = doctorService.getDoctorsByMajorName(departmentName);
	    model.addAttribute("doctors", doctors);
	    return "medicalCenter/doctorChoiceResult";
	}
	
	/**
	 * 특정 의사의 예약 가능 날짜 선택 페이지로 이동합니다.
	 * @param resDoctorName 선택된 의사 이름
	 * @param model Model 객체
	 * @return 날짜 선택 결과 페이지의 뷰 이름
	 */
	@GetMapping("reservation/date")
    public String dateChoice(@RequestParam("resDoctorName") String resDoctorName, Model model) {
        List<Date> dates = doctorService.getDateByDoctorName(resDoctorName);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy년MM월dd일HH:mm", Locale.KOREA);
        List<String> formattedDates = dates.stream()
                .map(date -> outputFormat.format(date))
                .collect(Collectors.toList());
        model.addAttribute("dates", formattedDates);
        return "medicalCenter/dateResult";
    }
	
	/**
	 * 특정 의사의 이름을 포스트 메서드를 이용하여 얻어옵니다.
	 * @param requestBody 요청 본문 맵 객체
	 * @return 의사 이름
	 */
	@ResponseBody
	@PostMapping("reservation/result")
	public String getDrNameByDrNoPost(@RequestBody Map<String, String> requestBody) {
	    String doctorNo = requestBody.get("doctorNo");
	    String doctorName = doctorService.getdrnamebydrno(doctorNo);
	    return doctorName;
	}
	
	/**
	 * 특정 의사의 부서를 포스트 메서드를 이용하여 얻어옵니다.
	 * @param requestBody 요청 본문 맵 객체
	 * @return 의사 부서
	 */
	@ResponseBody
	@PostMapping("reservation/doctorDep")
	public String getDepByDrNo(@RequestBody Map<String, String> requestBody) {
		String doctorNo = requestBody.get("doctorNo");
		String doctorDep = doctorService.getDepByDrNo(doctorNo);
		return doctorDep;
	}

	/**
	 * 특정 의사의 예약을 포스트 메서드를 이용하여 진행합니다.
	 * @param resDoctorName 선택된 의사 이름
	 * @param drApptTime 선택된 예약 시간
	 * @param paramMap 요청 본문 맵 객체
	 * @param loginMember 로그인 회원 정보
	 * @return 예약 결과 정보 맵
	 */
	@ResponseBody
	@PostMapping("reservation/doctorReservation")
	public Map<String, Object> reservation(@RequestParam("resDoctorName") String resDoctorName,
	                                       @RequestParam("drApptTime") String drApptTime,
	                                       @RequestBody Map<String, Object> paramMap,
	                                       @SessionAttribute("loginMember") Member loginMember) {
	    int memberNo = loginMember.getMemberNo();
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
	        response.put("redirectUrl", "/medicalCenter/reservationSuccess");
	        response.put("drRoom", drRoom);
	    } else {
	        response.put("message", "예약 실패");
	        response.put("redirectUrl", "/medicalCenter/reservation/doctorMatching");
	    }
	    return response;
	}
	
	/**
	 * 예약 성공 페이지로 이동합니다.
	 * @return 예약 성공 페이지의 뷰 이름
	 */
	@GetMapping("reservationSuccess")
	public String reservationSuccess() {
		return "medicalCenter/reservationSuccess";
	}
	
	/**
	 * 간병인 매칭 결과 페이지로 이동합니다.
	 * @return 간병인 매칭 결과 페이지의 뷰 이름
	 */
	@PostMapping("careGiversResult")
	public String careGiversResult() {
		return "medicalCenter/careGiversResult";
	}

    private final ClusteringService clusteringService;
    private final CareGiverClustering cgc = new CareGiverClustering();

    /**
     * 클라이언트에서 전달된 데이터를 기반으로 간병인 클러스터링을 수행하고 결과를 반환합니다.
     * @param formData 클라이언트에서 전달된 설문 데이터
     * @return 클러스터링 결과로 선택된 간병인 리스트
     * @throws IOException 입출력 예외 발생 시
     */
    @PostMapping("careGivers")
    @ResponseBody
    public List<CareGiver> clusterCaregivers(@RequestBody SurveyForm formData) throws IOException{
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
    private com.silver.shelter.careGiver.service.CaregiverService caregiverService;
    private AdminService adservice;

    /**
     * 회원이 선택한 간병인을 처리하는 메서드입니다.
     * @param caregiverId 선택된 간병인 ID
     * @param loginMember 로그인 회원 정보
     * @return ResponseEntity 객체로 간병인 정보 반환
     */
    @PostMapping("/selectCaregiver")
    @ResponseBody
    public ResponseEntity<?> selectCaregiver(@RequestParam("caregiverId") int caregiverId,
                                             @SessionAttribute("loginMember") Member loginMember) {
        try {
            caregiverService.selectCaregiver(caregiverId, loginMember.getMemberNo());
            CareGiver caregiverInfo = caregiverService.getCaregiverInfoById(caregiverId);
            loginMember.setCaregiversName(caregiverInfo.getCaregiversName());
            return ResponseEntity.ok().body(caregiverInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    /**
     * 로그인 회원의 예약된 날짜 목록을 가져오는 메서드입니다.
     * @param loginMember 로그인 회원 정보
     * @return ResponseEntity 객체로 예약된 날짜 목록 반환
     */
    @ResponseBody
    @PostMapping("getReservedDates")
    public ResponseEntity<List<DoctorAppointment>> getReservedDatesPost(@SessionAttribute("loginMember") Member loginMember) {
        List<DoctorAppointment> reservedDates = doctorService.getReservedDates(loginMember.getMemberNo());
        return ResponseEntity.ok().body(reservedDates);
    }

    /**
     * 특정 날짜에 예약된 의료 서비스를 가져오는 메서드입니다.
     * @param loginMember 로그인 회원 정보
     * @param clubResvTime 클라이언트에서 전송된 날짜/시간 정보
     * @return 예약된 의료 서비스 목록
     */
    @ResponseBody
    @PostMapping("getReservationsForDate")
    public List<DoctorAppointment> getReservationsForDate(
        @SessionAttribute("loginMember") Member loginMember,
        @RequestBody String clubResvTime) {
        
        DoctorAppointment reservation = DoctorAppointment.builder()
                .drApptTime(clubResvTime)
                .memberNo(loginMember.getMemberNo())
                .build();
        
        List<DoctorAppointment> reservations = doctorService.getReservationsForDate(reservation);
        return reservations;
    }

    /**
     * 예약 확인 페이지에서 선택한 날짜에 대한 예약 정보를 업데이트하는 메서드입니다.
     * @param loginMember 로그인 회원 정보
     * @param model Model 객체
     * @param date 선택된 날짜 정보
     * @param ra RedirectAttributes 객체
     * @return 예약 정보 업데이트 결과에 따른 뷰 경로
     */
    @GetMapping("reservationCheck/update/{date:[0-9]+}")
    public String updateReserv(@SessionAttribute("loginMember") Member loginMember,
                               Model model,
                               @PathVariable("date") String date,
                               RedirectAttributes ra) {
        
         String formattedDate = String.join("-", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
        
         DoctorAppointment reservation = DoctorAppointment.builder()
                    .drApptTime(formattedDate)
                    .memberNo(loginMember.getMemberNo())
                    .build();
         
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
    
    
    /**
     * 예약 정보를 삭제하는 메서드입니다.
     * @param loginMember 로그인 회원 정보
     * @param resDocNo 삭제할 예약의 의사 번호
     * @return 삭제된 예약 수
     */
    @ResponseBody
    @PostMapping("deleteReservation")
    public int deleteReservation(
        @SessionAttribute("loginMember") Member loginMember,
        @RequestBody int resDocNo) {

        return doctorService.deleteReservation(resDocNo);
    }

}
