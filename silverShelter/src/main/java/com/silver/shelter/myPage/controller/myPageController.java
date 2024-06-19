package com.silver.shelter.myPage.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.member.model.dto.Member;
import com.silver.shelter.myPage.model.service.MyPageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("myPage")
@Slf4j
@RequiredArgsConstructor
public class myPageController {

	public final MyPageService service;

	@ResponseBody
	@GetMapping("getReservedDates")
	public List<ClubReservation> getReservedDates(@SessionAttribute("loginMember")Member loginMember) {
		
		
		return service.getReservedDates(loginMember.getMemberNo());
	}
	
	@ResponseBody
	@PostMapping("getReservationsForDate")
	public List<ClubReservation> getReservationsForDate(@SessionAttribute("loginMember")Member loginMember,
														@RequestBody String clubResvTime) {
		
		log.info("현재 클릭된 시간은? == {} ", clubResvTime);
		
		ClubReservation reservation = ClubReservation.builder()
				.clubResvTime(clubResvTime)
				.memberNo(loginMember.getMemberNo())
				.build();
		
		
		return service.getReservationsForDate(reservation);
	}
	
	@GetMapping("myInfo")
	public String myPageMapping() {
		
		return "myPage/myInfo";
	}
	
	
	@GetMapping("myInfo/update/{date:[0-9]+}")
	public String updateReserv(@SessionAttribute("loginMember")Member loginMember,
								Model model,
								@PathVariable("date") String date,
								RedirectAttributes ra) {
		
		 String formattedDate = String.join("-", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
		
		 ClubReservation reservation = ClubReservation.builder()
					.clubResvTime(formattedDate)
					.memberNo(loginMember.getMemberNo())
					.build();
		 
		 log.info("여기에 뭐가 들어오는지 보여줘 == {}",formattedDate);
		List<ClubReservation> reservationList = service.getReservationsForDate(reservation);
		
		String path = null; 
		String message = null;
		
		if(reservationList.isEmpty()) {
			
			message = "예약한 날짜가 없습니다.";
			path = "redirect:/myPage/myInfo";
			
			ra.addFlashAttribute("message",message);
			
		} else {
			
			path = "myPage/updateReserv";
			model.addAttribute("reservationList",reservationList);
			log.info("예약 리스트들은 ? == {} ",reservationList);
			model.addAttribute("timeSlots", Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"));
			model.addAttribute("clubNames", Arrays.asList("게스트룸", "골프", "수영", "사우나", "게이트볼", "영화", "도예", "십자수"));
			model.addAttribute("formattedDate",formattedDate); 
		}
		
		
		return path;
	}
	
	/** 예약 수정 
	 * @param loginMember
	 * @param date
	 * @param reservationTime
	 * @param clubCode
	 * @param ra
	 * @return
	 */
	@PostMapping("myInfo/myReserUpdate/{date:[0-9]+}")
	public String myReserUpdate(@SessionAttribute("loginMember")Member loginMember,
			@PathVariable("date") String date,
			@RequestParam("reservationTime") String reservationTime,
			@RequestParam("clubCode") int clubCode,
			RedirectAttributes ra) {
		
		 String formattedDate = String.join("-", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
		
		 String resetTime = formattedDate += " "+reservationTime;
		 
		 log.info("새로들어온 시간은?  == {}",resetTime);
		 ClubReservation clubresv = ClubReservation.builder()
				 					.clubResvTime(resetTime)
				 					.memberNo(loginMember.getMemberNo())
				 					.clubCode(clubCode)
				 					.build();
				 					
		int result = service.myReserUpdate(clubresv);
		log.info("뭐가 찍히려나 == {}",clubresv.getClubResvTime());
		
		String path = null;
		String message = null;
		
		
		if(result > 0) {
			
			message = "예약이 변경 되었습니다.";
			path = "redirect:/myPage/myInfo";
		
		} else {
			
			message = "예약이 변경 실패...";
			path = "myPage/updateReserv";
			
		}
		
		ra.addFlashAttribute("message",message);
		
		return path;
	}
	
	/** 예약 삭제 
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("deleteReservation")
	public int deleteReservation(@RequestBody Map<String, Object>paramMap,
								 @SessionAttribute("loginMember")Member loginMember) {
		
		log.info("처음 들어오는 값은? == {}",paramMap.get("clubCode"));
		log.info("처음 들어오는 값은? == {}",paramMap.get("clubResvNo"));
		
		ClubReservation clubReserv = ClubReservation.builder()
									.clubCode(Integer.parseInt((String)paramMap.get("clubCode")))
									.clubResvNo(Integer.parseInt((String)paramMap.get("clubResvNo")))
									.memberNo(loginMember.getMemberNo())
									.build();
		
		log.info("값이 뭐게 == {}",clubReserv);
		
		return service.deleteReservation(clubReserv);
	}
	
}