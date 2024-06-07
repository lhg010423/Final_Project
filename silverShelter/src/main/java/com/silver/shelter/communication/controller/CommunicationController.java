package com.silver.shelter.communication.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.communication.model.service.CommunicationService;
import com.silver.shelter.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("communication")
@RequiredArgsConstructor
public class CommunicationController {
	
	private final CommunicationService service;
	
	
	@GetMapping("communication")
	public String communication() {
		
		return "communication/communication";
	}
	
	
	/** 게스트룸 예약 하기 
	 * @param paramMap
	 * @param loginMember
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("guestReservation")
	public int reservation(@RequestBody Map<String, Object> paramMap,
						   @SessionAttribute("loginMember")Member loginMember) {
		
		
		// 로그인한 회원의 번호 가져오기
	    loginMember.getMemberNo();
	    
	    // ClubReservation 객체 생성 및 데이터 설정
	    ClubReservation reservation = new ClubReservation();
	    
	    // paramMap에서 clubResvTime 값을 String으로 가져와 설정
	    reservation.setClubResvTime((String) paramMap.get("clubResvTime"));
	    
	    // 회원 번호 설정
	    reservation.setMemberNo(loginMember.getMemberNo());
	    
	    // paramMap에서 clubCode 값을 String으로 가져온 후, 이를 Integer로 변환하여 설정
	    String clubCodeStr = paramMap.get("clubCode").toString();
	    reservation.setClubCode(Integer.parseInt(clubCodeStr));
	    
	    
	    // 예약한 시간에 이미 예약이 있는지 확인 
	    int count = service.selectGuestRoomReservation(reservation);
	    
	    if(count > 0) {
	    	
	    	// 있다면 3 반환 
	    	return 3;
	    	
	    } else {
	    	
		    // 서비스 메서드를 호출하여 예약 처리
		    int result = service.guestRoomReservation(reservation);
		    
			
			if(result > 0) {
					
				return result; 
			
			} else {
			
				return result;
			}
			
	    }
	}
	
	@GetMapping("sportReservation")
	public String sportReservation() {
		
		return "communication/sportReservation";
	}
	
	
}
