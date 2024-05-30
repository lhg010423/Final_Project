package com.silver.shelter.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.silver.shelter.admin.model.service.AdminService;
import com.silver.shelter.member.model.dto.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

	private final AdminService service;
	
	
	/** 한눈에 보기
	 * @return
	 */
	@GetMapping("adminMain")
	public String adminMain() {
		
		return "admin/adminMain";
	}
	
	
	
	/** 회원 정보 조회
	 * @return
	 */
	@GetMapping("adminSelect")
	public String adminSelect(
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model,
//			@RequestParam Member memberNo,
			@RequestParam Map<String, Object> paramMap
			
			) {
		
		
		Map<String, Object> map = null;
				
//		paramMap.put("memberNo", memberNo);
		
		// 검색 안했을 때
		if(paramMap.get("key") == null) {
			map = service.memberAllSelect(cp);
			
			
		// 검색 했을 때
		} else {
			map = service.memberSearchSelect(paramMap, cp);
		
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("memberList", map.get("memberList"));
		
		System.out.println(map.get("memberList"));
		System.out.println(map.get("pagination"));
		
		return "admin/adminSelect";
	}
	
	
	
	
	
	
	
	
	
	
	
	@ResponseBody
	@PostMapping("adminSelect")
	public Map<String, Object> adminSelect(@RequestBody Map<String, Object> paramMap) {
		
		// js로 다시 보낼 map
		Map<String, Object> map = new HashMap<>();
		
		// 회원 상세정보 조회
		Member memberInfo = service.adminDetailSelect(paramMap);
		
		String memberAddress = memberInfo.getMemberAddress();
		
		// 이름, 아이디, 이메일, 전화번호, 보호자 전화번호, 방번호
		map.put("memberName", memberInfo.getMemberName());
		map.put("memberId", memberInfo.getMemberId());
		map.put("memberEmail", memberInfo.getMemberEmail());
		map.put("memberTel", memberInfo.getMemberTel());
		map.put("guardianTel", memberInfo.getGuardianTel());
		map.put("caregiversName", memberInfo.getCaregiversName());
		map.put("roomNo", memberInfo.getRoomNo());
		
		if(memberAddress != null) {
			
			String[] arr = memberAddress.split("\\^\\^\\^");
			
			map.put("postCode", arr[0]);
			map.put("address", arr[1]);
			map.put("detailAddress", arr[2]);
		}
		
		
		log.info("member {}" , map);
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 회원 서류 관리
	 * @return
	 */
	@GetMapping("adminDocument")
	public String adminDocument() {
		return "admin/adminDocument";
	}
	
	
	/** 예약
	 * @return
	 */
	@GetMapping("reservation")
	public String reservation() {
		return "admin/reservation";
	}
	
	
	/** 게시판 관리
	 * @return
	 */
	@GetMapping("boardList")
	public String boardList() {
		return "admin/boardList";
	}
	
	
	/** 요양사 관리
	 * @return
	 */
	@GetMapping("caregiver")
	public String caregiver() {
		return "admin/caregiver";
	}
	
	
	
}
