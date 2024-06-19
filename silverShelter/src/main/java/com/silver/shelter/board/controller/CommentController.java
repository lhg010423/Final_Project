package com.silver.shelter.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	@GetMapping("")
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		return service.select(boardNo);
	}
	
	
	
}
