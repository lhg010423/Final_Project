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
import com.silver.shelter.examination.model.dto.Examination;
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
	
	
	

	/** 회원 정보 조회 및 검색
	 * @param cp
	 * @param model : 게시글 회원 조회에 사용할 데이터 전달용
	 * @param paramMap : 안에 key, query값이 있음, 검색할 경우에만 사용
	 * @return
	 */
	@GetMapping("adminSelect")
	public String adminSelect(
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model,
//			@RequestParam(value="memberNo", required = false) int memberNo,
			@RequestParam Map<String, Object> paramMap
			
			) {
		
		// 회원 조회한 결과 저장용 Map
		Map<String, Object> map = null;
		
		
		// 검색 안했을 때
		if(paramMap.get("key") == null) {
			map = service.memberAllSelect(cp);
			
			
		// 검색 했을 때
		} else {
			map = service.memberSearchSelect(paramMap, cp);
		
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("memberList", map.get("memberList"));
		
		// 그냥 확인용 코드
		System.out.println(map.get("memberList"));
		System.out.println(map.get("pagination"));
		
		return "admin/adminSelect";
	}
	
	
	
	
	/** 회원 상세 조회
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("adminSelect")
	public Map<String, Object> adminSelect(
			@RequestBody int memberNo
			) {
		
//			@RequestBody Member memberNo
		// js로 다시 보낼 map
		Map<String, Object> map = new HashMap<>();
		
		
//		System.out.println("memberNo : " + memberNo);
		
		// 회원 상세정보 조회
		Member memberInfo = service.adminDetailSelect(memberNo);

		if (memberInfo == null) {
	        // 회원 정보가 없는 경우
	        map.put("error", "회원 정보를 찾을 수 없습니다.");
	        return map;
	    }
		
		
//		log.info("memberInfo {}"+ memberInfo);
		
		System.out.println("memberInfo 테스트@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +  memberInfo);
		
		

		// 이름, 아이디, 이메일, 전화번호, 보호자 전화번호, 방번호
		map.put("memberName", memberInfo.getMemberName());
		map.put("memberId", memberInfo.getMemberId());
		map.put("memberEmail", memberInfo.getMemberEmail());
		map.put("memberTel", memberInfo.getMemberTel());
		map.put("guardianTel", memberInfo.getGuardianTel());
		map.put("caregiversName", memberInfo.getCaregiversName());
		map.put("roomNo", memberInfo.getRoomNo());
		
		if(memberInfo.getMemberAddress() != null) {
			
			String memberAddress = memberInfo.getMemberAddress();
			
			String[] arr = memberAddress.split("\\^\\^\\^");
			
			map.put("postCode", arr[0]);
			map.put("address", arr[1]);
			map.put("detailAddress", arr[2]);
		}
		
		// 이름, 아이디, 이메일, 전화번호, 보호자 전화번호, 방번호

		
		log.info("member {}", map.get("memberInfo"));
		
		
		return map;
	}
	
	
	
	
	
	/** 회원 서류 관리
	 * @return
	 */
	@GetMapping("adminDocument")
	public String adminDocument(
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model,
//			@RequestParam(value="memberNo", required = false) int memberNo,
			@RequestParam Map<String, Object> paramMap
			) {
		
		Map<String, Object> map = null;
		
		
		// 검색 안했을 때
		if(paramMap.get("key") == null) {
			map = service.examinationAllSelect(cp);
			
			
		// 검색 했을 때
		} else {
			map = service.examinationSearchSelect(paramMap, cp);
		
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("examinationList", map.get("examinationList"));
		
		System.out.println(map.get("pagination"));
		System.out.println(map.get("examinationList"));
		
		return "admin/adminDocument";
	}
	
	
	
	
	/** 회원 상세 조회
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@PostMapping("adminDocument")
	public Map<String, Object> adminDocument(
			@RequestBody int examId
			) {
		
//			@RequestBody Member memberNo
		// js로 다시 보낼 map
		Map<String, Object> map = new HashMap<>();
		
		
		// 회원 상세정보 조회
		Examination examInfo = service.examinationDetailSelect(examId);

		if (examInfo == null) {
	        // 회원 정보가 없는 경우
	        map.put("error", "회원 정보를 찾을 수 없습니다.");
	        return map;
	    }
		
		
//		log.info("memberInfo {}"+ memberInfo);
		
		System.out.println("examInfo 테스트@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +  examInfo);
		
		

		// 이름, 아이디, 이메일, 전화번호, 보호자 전화번호, 방번호
		map.put("examId", examInfo.getExamId());
		map.put("examName", examInfo.getExamName());
		map.put("examEmail", examInfo.getExamEmail());
		map.put("examPhone", examInfo.getExamPhone());
		map.put("examStatus", examInfo.getExamStatus());
		map.put("examDate", examInfo.getExamDate());
		map.put("examRoom", examInfo.getExamRoom());

		System.out.println("examMap 테스트@@@@@@@@@@@@@@@@@" + map); // 잘됨
		
		return map;
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
