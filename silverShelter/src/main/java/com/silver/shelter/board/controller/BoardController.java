package com.silver.shelter.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.silver.shelter.board.model.service.BoardService;
import com.silver.shelter.member.model.dto.Member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService service;

	
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
	 * @param model : 데이터 전달용
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(
			@PathVariable("boardCode") int boardCode,
			@PathVariable("boardNo") int boardNo,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam Map<String, Object> paramMap,
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			HttpSession session,
			Model model
			) {
		
		// 데이터 전달용 객체 생성
		Map<String, Object> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());			
		}
		
		
		Map<String, Object> boardDetail = service.boardDetail(map);
		
		
		// 검색 쿼리 및 페이지 번호 저장(목록으로 버튼 클릭 시 사용할 것)
		session.setAttribute("key", paramMap.get("key"));
		session.setAttribute("query", paramMap.get("query"));
		session.setAttribute("cp", cp);
		
		
		model.addAttribute("boardList", boardDetail.get("boardList"));
		model.addAttribute("canModify", boardDetail.get("canModify"));
		model.addAttribute("canDelete", boardDetail.get("canDelete"));
		
		
		return "board/boardDetail";
	}
	
	
	
	
	
	
	
	
	
	
	

}
