package com.silver.shelter.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.silver.shelter.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService service;

	
	/** 공지 게시판
	 * @return
	 */
	@GetMapping("notice")
	public String notice() {
		return "/board/notice";
	}
	
	
	
	

}
