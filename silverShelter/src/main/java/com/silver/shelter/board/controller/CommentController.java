package com.silver.shelter.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silver.shelter.board.model.dto.Comment;
import com.silver.shelter.board.model.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController // 비동기 요청만 받는 컨트롤러
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService service;
	
	
	/** 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
//	@GetMapping("")
//	public Map<String, Object> select(
//			@RequestParam("boardNo") int boardNo,
//			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
//			Model model,
//			@RequestParam Map<String, Object> paramMap
//			) {
//		
//		// return에 if문 못씀 삼항 연산자로 써야함
//		
//		Map<String, Object> map = null;
//		
//		
//		if(paramMap.get("key") == null) {
//			map = service.commentAllSelect(boardNo, cp);
//			
//		} else {
//			
//			paramMap.put("boardNo", boardNo);
//			
//			map = service.commentSearchSelect(paramMap, cp);
//			
//		}
//		
//		return map;
//	}
	
	@GetMapping("") // get요청온거를 잡아줌
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		return service.select(boardNo);
	}
	
	
	/** 댓글, 대댓글 작성
	 * @param comment
	 * @return
	 */
	@PostMapping("")
	public int insert(@RequestBody Comment comment) {
		return service.insert(comment);
	}
	
	
	/** 댓글 삭제
	 * @param commentNo
	 * @return
	 */
	@DeleteMapping("")
	public int delete(@RequestBody int commentNo) {
		return service.delete(commentNo);
	}
	
	
	/** 댓글 수정
	 * @param comment
	 * @return
	 */
	@PutMapping("")
	public int update(@RequestBody Comment comment) {
		return service.update(comment);
	}
	
	
	
	
	
	
	
}
