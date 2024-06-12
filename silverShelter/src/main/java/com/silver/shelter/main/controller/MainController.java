package com.silver.shelter.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.silver.shelter.board.model.dto.Board;
import com.silver.shelter.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MainController {
	
	@Value("${my.public.data.service.key.decode}")
	private String decodeServiceKey;
	
	// main말고 boardService로 넘어감
	private final BoardService boardService;
	
	
	@RequestMapping("/")
	public String mainPage(Model model) {
		
		// 메인페이지 하단 게시글 조회
		Map<String, Object> boardList = boardService.mainBoardSelect();
		
//		List<Board> boardList = boardService.mainBoardSelect();
		
		model.addAttribute("boardList", boardList);
		
		
		return "common/main";
	}
	
	@GetMapping("/getServiceKey")
	@ResponseBody
	public String getServiceKey() {
		
		return decodeServiceKey;
	}
	
	
	
	

}
