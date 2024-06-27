package com.silver.shelter.board.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.silver.shelter.board.model.dto.Board;
import com.silver.shelter.board.model.dto.Comment;
import com.silver.shelter.board.model.service.BoardService;
import com.silver.shelter.board.model.service.CommentService;
import com.silver.shelter.member.model.dto.Member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService service;
	
	private final CommentService cs;

	
	/** 게시판 게시글 조회하기
	 * @return
	 */
	/**
	 * @param cp : Pagination 값 가져오기, 값이 없을 때는 기본값 1
	 * @param model : 게시글 조회한 결과 전송용
	 * @param paramMap : 검색창의 key, query 값이 있음
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}")
	public String notice(
			@PathVariable("boardCode") int boardCode,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model,
			@RequestParam Map<String, Object> paramMap
			) {
		
		// 공지 게시글 조회한 결과 저장용 Map
		Map<String, Object> map = null;
		
		// 검색 안했을 때
		if(paramMap.get("key") == null) {
			map = service.boardAllSelect(boardCode, cp);
			
		// 검색 했을 때
		} else {
			
			paramMap.put("boardCode", boardCode);
			
			System.out.println(paramMap);
			
			System.out.println(paramMap.get("key"));
			
			map = service.boardSearchSelect(paramMap, cp);
			
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		
		
		return "board/boardList";
	}
	
	
	
	/** 게시글 상세보기
	 * @param boardCode : 공지, 자유, 문의 게시판 구분용
	 * @param boardNo : 게시글 번호
	 * @param cp : 목록으로 버튼 클릭시 전 페이지로 돌아가기 위해 씀
	 * @param paramMap : 검색했을 때 목록으로 버튼 클릭시 key, query값으로
	 * 					 전페이지로 돌아가기 위해 씀
	 * @param loginMember : 로그인한 회원확인용
	 * @param req : 요청에 담김 쿠키 얻어오기
	 * @param resp : 새로운 쿠키 만들어서 응답하기
	 * @param model : 데이터 전달용
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(
			@PathVariable("boardCode") int boardCode,
			@PathVariable("boardNo") int boardNo,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam Map<String, Object> paramMap, // 게시글 검색,
			@RequestParam(value="commentPage", required = false, defaultValue = "1") int commentPage,
			@RequestParam(value="commentKey", required = false) String commentKey, // 댓글 검색 키
			@RequestParam(value="commentQuery", required = false) String commentQuery, // 댓글 검색 쿼리
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			HttpSession session,
			HttpServletRequest req,
			HttpServletResponse resp,
			Model model
			) {
		
		// 데이터 전달용 객체 생성
		Map<String, Object> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		// 로그인 했을 때만 memberNo 추가
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());			
		}
		
		// 게시글 상세정보 불러오기
		Board board = service.boardDetailSelect(map);
		
		
		String path = null;
		
		// 조회 결과가 없을 때
		if(board == null) {
			// 수정@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			path = "redirect:/board/" + boardCode;
			
		} else {
			
			// 로그인 안함 || 로그인한 회원 != 게시글 작성자
			if(loginMember == null ||
					loginMember.getMemberNo() != board.getMemberNo()) {
				
				
				Cookie[] cookies = req.getCookies();
				
				Cookie c = null;
				
				for(Cookie temp : cookies) {
					
					if(temp.getName().equals("readBoardNo")) {
						c = temp;
						break;
					}
				}
				
				
				int result = 0;
				
				
				// readBoardNo 에 쿠키가 없을 때
				if(c == null) {
					
					c = new Cookie("readBoardNo", "[" + boardNo + "]");
					result = service.readCountUpdate(boardNo);
					
				} else {
					
					if(c.getValue().indexOf("[" + boardNo + "]") == -1) {
						
						c.setValue(c.getValue() + "[" + boardNo + "]");
						result = service.readCountUpdate(boardNo);
					}
				}
				
				
				// 조회수 증가 성공
				if(result > 0) {
					board.setReadCount(result);
					
					// 쿠키 적용 경로 설정
					c.setPath("/");
					
					// 수명 지정
					
					// 현제 시간 얻어오기
					LocalDateTime now = LocalDateTime.now();
					
					// 다음날 지정
					LocalDateTime nextDayMidnight = now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
					
					// 다음날 자정까지 남은 시간 계산 - now, nextDayMidnight 사이를 초단위로 계산
					long secondsUntilNextDay = Duration.between(now, nextDayMidnight).getSeconds();
					
					// 쿠키 수명 설정
					c.setMaxAge((int)secondsUntilNextDay);
					
					resp.addCookie(c); // 클라이언트로 전달
				}
				
			}
			
			
			path = "board/boardDetail";
		}
		
		
		// 검색 쿼리 및 페이지 번호 저장(목록으로 버튼 클릭 시 사용할 것)
		session.setAttribute("key", paramMap.getOrDefault("key", ""));
		session.setAttribute("query", paramMap.getOrDefault("query", ""));
//		session.setAttribute("cp", cp);
		
		// 수정, 삭제 권한
		boolean author = false;
		
		// 로그인 함
		if(loginMember != null ) {
			
			// 로그인한 회원 == 게시글 작성자 ||
			// 		로그인한 회원 == 관리자 계정
			if(loginMember.getMemberNo() == board.getMemberNo() || 
					loginMember.getMemberRole() == 1) {

				author = true;
			}
		}
		
		// 댓글 --------------------------------------------------
		
		// 결과 저장용 Map
		Map<String, Object> commentMap = null;
		
		Map<String, Object> cparamMap = new HashMap<>();
		cparamMap.put("boardNo", boardNo);
		cparamMap.put("commentKey", commentKey);
		cparamMap.put("commentQuery", commentQuery);
		
		
		if(commentKey == null) {
			
			commentMap = cs.commentAllSelect(boardNo, commentPage);
		} else {
			
			commentMap = cs.commentSearchSelect(cparamMap, commentPage);
		}
		
		System.out.println(commentKey);
		System.out.println(commentQuery);
		
		model.addAttribute("board",board);
		model.addAttribute("author", author);
		model.addAttribute("cp", cp);
		model.addAttribute("commentKey", commentKey);
		model.addAttribute("commentQuery", commentQuery);
		model.addAttribute("commentPage", commentPage);
		model.addAttribute("commentList", commentMap.get("commentList"));
		model.addAttribute("pagination", commentMap.get("pagination"));
		model.addAttribute("commentCount", commentMap.get("commentCount"));
		
		
		return path;
	}
	
	
	/** 게시글 작성 폼으로 이동
	 * @param boardCode
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(@PathVariable("boardCode") int boardCode) {
		return "board/boardWrite";
	}
	
	
	/** 게시글 작성
	 * @param boardCode
	 * @param inputBoard
	 * @param loginMember
	 * @param ra
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(
			@PathVariable("boardCode") int boardCode,
			@ModelAttribute Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember,
//			@RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra)
			throws IllegalStateException, IOException{
		
		
		inputBoard.setBoardCode(boardCode);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		int boardNo = service.boardInsert(inputBoard);
		
		String path = null;
		String message = null;
		
		if(boardNo > 0) {
			// /board/1/2000
			path = "/board/" + boardCode + "/" + boardNo; // 상세 조회
			message = "게시글이 작성 되었습니다";
			
		} else {
			// 상대경로
			path = "insert";
			message = "게시글 작성 실패";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	
	
	
	
	/** 게시글 좋아요 체크/해제
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping("like")
	public int boardList(@RequestBody Map<String, Integer> map) {
		return service.boardLike(map);
	}
	
	
	
	/** 게시글 수정 페이지로 이동
	 * @param boardCode
	 * @param boardNo
	 * @param cp
	 * @param key
	 * @param query
	 * @param model
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/boardUpdate")
	public String boardUpdate(
			@PathVariable("boardCode") int boardCode,
			@PathVariable("boardNo") int boardNo,
//			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
//		    @RequestParam(value="key", required = false, defaultValue = "") String key,
//		    @RequestParam(value="query", required = false, defaultValue = "") String query,
		    Model model
			
			) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		// 게시글 정보 불러오기
		Board board = service.boardDetailSelect(map);
		
		model.addAttribute("board",board);
//		model.addAttribute("cp", cp);
//		model.addAttribute("key", key);
//		model.addAttribute("query", query);
		
		log.info("board {}" + board);
		
		return "board/boardUpdate";
	}
	
	
	
	/** 게시글 수정하기
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/boardUpdate")
	public int boardUpdate(@RequestBody Map<String, Object> map) {
		
		return service.boardUpdate(map);
	}
	
	
	
	/** 게시글 삭제하기
	 * @param boardNo
	 * @return
	 */
	@ResponseBody
	@PostMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/boardDelete")
	public int boardDelete(@RequestBody int boardNo) {
		return service.boardDelete(boardNo);
	}
	
	
	/*
	 * @ResponseBody
	 * 
	 * @GetMapping("commentSelect") public List<Comment> commentSelect(
	 * 
	 * @RequestParam("boardNo") int boardNo ) { return
	 * service.commentSelect(boardNo); }
	 */
	
	
	/** 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
	@GetMapping("select")
	public String select(
			@RequestParam("boardNo") int boardNo,
			@RequestParam("boardCode") int boardCode,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			Model model,
			@RequestParam Map<String, Object> paramMap
			) {
		
		// return에 if문 못씀 삼항 연산자로 써야함
		
		Map<String, Object> map = null;
		
		
		if(paramMap.get("key") == null) {
			map = cs.commentAllSelect(boardNo, cp);
			
		} else {
			
			paramMap.put("boardNo", boardNo);
			
			map = cs.commentSearchSelect(paramMap, cp);
			
		}
		
		model.addAttribute("commentList", map.get("commentList"));
		model.addAttribute("commentCount", map.get("commentCount"));
		model.addAttribute("pagination", map.get("pagination"));
		
		
		return "admin/" + boardCode + "/" + boardNo;
	}
	
	

}
