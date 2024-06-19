package com.silver.shelter.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.board.model.dto.Comment;

@Mapper
public interface CommentMapper {

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
	List<Comment> select(int boardNo);

}
