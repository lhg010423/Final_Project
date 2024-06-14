package com.silver.shelter.myPage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	/*
	 * @GetMapping("myPage") 
	 * public String myPageMapping( HttpSession session, 
	 * 								Model model) {
	 * 
	 * Member loginMember = (Member)session.getAttribute("loginMember");
	 * 
	 * if(loginMember == null) {
	 * 
	 * return "redirect:/login"; }
	 * 
	 * model.addAttribute("loginMember",loginMember); return "myPage/myPage"; }
	 */
	
//	@GetMapping("myPage")
//	public String myPageMapping(@SessionAttribute("loginMember")Member loginMember,
//			Model model) {
//
//		List<ClubReservation> reservList = service.selectReserv(loginMember.getMemberNo());
//		
//		
//		String message = null;
//		
//		
//		if(reservList.isEmpty()) {
//			
//			message = "예약된 날짜가 없습니다.";
//			
//		}
//		
//		model.addAttribute("reservList",reservList);
//		
//		return "myPage/myPage";
//		
//	}



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

	@GetMapping("myInfo/update")
	public String updateReserv(@SessionAttribute("loginMember")Member loginMember,
								Model model,
								RedirectAttributes ra) {
		
		
		List<ClubReservation> reservationList = service.selectReserv(loginMember.getMemberNo());
		
		String path = null; 
		String message = null;
		
		if(reservationList.isEmpty()) {
			
			message = "예약한 날짜가 없습니다.";
			path = "redirect:/myPage/myInfo";
			
			ra.addFlashAttribute("message",message);
			
		} else {
			
			path = "myPage/updateReserv";
			
			model.addAttribute("reservationList",reservationList);
			
		}
		return path;
	}
	
	
}
