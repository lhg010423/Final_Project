package com.silver.shelter.board.model.service;

import java.util.List;

import com.silver.shelter.board.model.dto.Comment;

public interface CommentService {

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
	List<Comment> select(int boardNo);

}
