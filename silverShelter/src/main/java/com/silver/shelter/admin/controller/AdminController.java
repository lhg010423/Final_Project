package com.silver.shelter.admin.controller;

import java.util.Arrays;
import java.util.HashMap;
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

import com.silver.shelter.admin.model.dto.Reservation;
import com.silver.shelter.admin.model.service.AdminService;
import com.silver.shelter.board.model.dto.Board;
import com.silver.shelter.board.model.service.BoardService;
import com.silver.shelter.board.model.service.CommentService;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.clubReservation.model.dto.ClubReservation;
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
	private final BoardService bs;
	private final CommentService cs;
	
	
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
		
		// js로 다시 보낼 map
		Map<String, Object> map = new HashMap<>();
		
		// 회원 상세정보 조회
		Member memberInfo = service.adminDetailSelect(memberNo);

		if (memberInfo == null) {
	        // 회원 정보가 없는 경우
	        map.put("error", "회원 정보를 찾을 수 없습니다.");
	        return map;
	    }
		
		
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
			@RequestBody Map<String, Integer> paramMap
			) {
		
//			@RequestBody Member memberNo
		// js로 다시 보낼 map
		Map<String, Object> map = new HashMap<>();
		
		
		// 회원 상세정보 조회
		Examination examInfo = service.examinationDetailSelect(paramMap.get("examId"));
		
		System.out.println(examInfo);

		if (examInfo == null) {
	        // 회원 정보가 없는 경우
	        map.put("error", "회원 정보를 찾을 수 없습니다.");
	        return map;
	    }
		
		
//		log.info("memberInfo {}"+ memberInfo);
		
		System.out.println("examInfo 테스트@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +  examInfo); // 잘됨
		
		

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
	
	

	/** 서류 심사 
	 * @param examId
	 * @return
	 */
	@ResponseBody
	@PostMapping("updateAdminDocument")
	public int updateAdminDocument(@RequestBody Map<String, Integer> map) {
		

		return service.updateAdminDocument(map.get("examId"));
	}
	
	
	/** 서류 심사 통과 시 이메일 전송
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping("signUpAdminDocument")
	public int signUpAdminDocument(@RequestBody Map<String, Integer> map) {
		
		String url = service.signUpAdminDocument("signUp",map.get("examId"));
		
		log.info("url 주소는? : "+ url);
		
		if(url != null) {
			
			return 1;
		}
		
		return 0;
	}

	
	/** 일정 예약 페이지 이동
	 * @return
	 */
	@GetMapping("reservation")
	public String reservation() {
		return "admin/reservation";
	}
	
	
	/** 일정 유무 조회
	 * @return
	 */
	@ResponseBody
	@GetMapping("getReservedDates")
	public List<Reservation> getReservedDates(@RequestParam Map<String, Object> paramMap) {
		
		return service.getReservedDates();
	}
	
	
//	@ResponseBody
//	@PostMapping("reservation")
//	public List<Reservation> reservationAllSelect(@RequestBody String resvTime) {
//		
//		ClubReservation reservation = ClubReservation.builder()
//				.clubResvTime(resvTime)
////				.memberNo(loginMember.getMemberNo())
//				.build();
//		
//		return service.reservationAllSelect(reservation);
//	}
	
	
	
	/** 특정 날짜의 예약 목록을 가져오는 함수
	 * @param clubResvTime
	 * @return
	 */
	@ResponseBody
	@PostMapping("getReservationsForDate")
	public List<Reservation> getReservationsForDate(@RequestBody String clubResvTime) {
		
//		Reservation reservation = Reservation.builder()
//				.reservationTime(clubResvTime)
//				.build();
		System.out.println("clubResvTime @@@@@@@@@ "+clubResvTime);
		
//		List<Reservation> re = service.getReservationsForDate(clubResvTime);
		
//		System.out.println(re);
		return service.getReservationsForDate(clubResvTime);
	}
	
	
	
	
	
	/** 게시판 관리
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}")
	public String boardList(
			@PathVariable("boardCode") int boardCode,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model,
			@RequestParam Map<String, Object> paramMap
			) {
		
		// 공지 게시글 조회한 결과 저장용 Map
		Map<String, Object> map = null;
		
		// 검색 안했을 때
		if(paramMap.get("key") == null) {
			map = bs.boardAllSelect(boardCode, cp);
			
		// 검색 했을 때
		} else {
			
			paramMap.put("boardCode", boardCode);
			
			System.out.println(paramMap);
			
			System.out.println(paramMap.get("key"));
			
			map = bs.boardSearchSelect(paramMap, cp);
			
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		log.info("pagination {}" + map.get("pagination"));
		log.info("boardList {}" + map.get("boardList"));
		System.out.println("boardList @@@@@@@@@" + map.get("boardList"));
		
		return "admin/boardList";
	}
	
	
	
	
	
	/** 게시글 상세 조회
	 * @param boardCode
	 * @param boardNo
	 * @param loginMember
	 * @param session
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("boardList")
	public Map<String, Object> boardList(
//			@PathVariable("boardCode") int boardCode,
			@RequestBody int boardNo
//			@SessionAttribute("loginMember") Member loginMember,
//			HttpSession session,
//			HttpServletRequest req,
//			HttpServletResponse resp
//			Model model
			
			) {
		
		System.out.println("컨트롤러 진입 확인");  // 디버깅 출력
		
		Map<String, Object> map = new HashMap<>();
		
		Map<String, Object> paramMap = new HashMap<>();
//		paramMap.put("boardCode", boardCode);
		paramMap.put("boardNo", boardNo);
		
//		int boardNo = (int) paramMap.get("boardNo");
		
		Board board = service.boardDetailSelect(boardNo);
//		List<Comment> commentList = cs.select(boardNo);
		
		System.out.println("board 확인용"+board);
		
		map.put("boardTitle", board.getBoardTitle());
		map.put("boardContent", board.getBoardContent());
		map.put("memberName", board.getMemberName());
		map.put("boardWriteDate", board.getBoardWriteDate());
		map.put("readCount", board.getReadCount());
		map.put("boardNo", board.getBoardNo());
		map.put("boardCode", board.getBoardCode());
//		map.put("commentList", commentList);
		
		
		return map;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 요양사 관리
	 * @return
	 */
	@GetMapping("caregivers")
	public String caregiver(
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model,
			@RequestParam Map<String, Object> paramMap
			
			) {
		
		// 결과 저장용 Map
		Map<String, Object> map = null;
		
		// 검색 안했을 때
		if(paramMap.get("key") == null) {
			map = service.caregiversAllSelect(cp);
			
			
		// 검색 했을 때
		} else {
			map = service.caregiversSearchSelect(paramMap, cp);
		
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("caregiversList", map.get("caregiversList"));
		
		// 그냥 확인용 코드
		System.out.println(map.get("caregiversList"));
		System.out.println(map.get("pagination"));
		
		
		return "admin/caregivers";
	}
	
	@ResponseBody
	@PostMapping("caregivers")
	public Map<String, Object> caregivers(@RequestBody int caregiversNo) {
		
		Map<String, Object> map = new HashMap<>();
		
		CareGiver caregiversInfo = service.caregiversDetailSelect(caregiversNo);
		
		
		map.put("caregiversNo", caregiversInfo.getCaregiversNo());
		map.put("caregiversName", caregiversInfo.getCaregiversName());
		map.put("caregiversAge", caregiversInfo.getCaregiversAge());
		map.put("caregiversGender", caregiversInfo.getCaregiversGender());
		map.put("caregiversTel", caregiversInfo.getCaregiversTel());
		map.put("caregiversExperience", caregiversInfo.getCaregiversExperience());
		map.put("caregiversWorkHours", caregiversInfo.getCaregiversWorkHours());
		map.put("caregiversRole", caregiversInfo.getCaregiversRole());
		
		
		return map;
	}
	
	
}
