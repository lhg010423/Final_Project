package silverShelter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

//@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

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
	public String adminSelect() {
		return "admin/adminSelect";
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
