package silverShelter.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import silverShelter.board.model.service.BoardService;

@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {
	
//	private final BoardService service;
//	
//	
//	public String boardListSelect(
//			@PathVariable("boardCode") int boardCode,
//			@RequestParam(value="cp", required = false, defaultValue = "1"),
//			Model model,
//			@RequestParam Map<String, Object> paramMap
//			
//			) {
//		
//		return "";
//	}

}
