package com.silver.shelter.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.silver.shelter.board.model.service.BoardService;

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
	
	
	
	

}
