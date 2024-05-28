package silverShelter.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import silverShelter.admin.model.service.AdminService;
import silverShelter.member.model.dto.Member;

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
//			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model,
			@RequestParam Map<String, Object> paramMap
			) {
		
		
		Map<String, Object> map = null;
				
		
		// 검색 안했을 때
		if(paramMap.get("key") == null) {
			map = service.memberALlSelect();
			
			
		// 검색 했을 때
		}
		
//		else {
//			map = service.memberSearchSelect(paramMap);
//		
//		}
		
		model.addAttribute("memberList", map.get("memberList"));
		
		
//		log.info((String) map.get("memberList"));
		
		return "admin/adminSelect";
	}
	
	
	
	
	
	
	
	
	
	
	
	@ResponseBody
	@PostMapping("adminSelect")
	public Member adminSelect(@RequestBody Member member,
								Model model) {
		
		log.info("member {}" , member);
		
		
		return service.adminDetailSelect(member);
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
