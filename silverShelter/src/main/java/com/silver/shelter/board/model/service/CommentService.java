package com.silver.shelter.board.model.service;

import java.util.List;
import java.util.Map;

import com.silver.shelter.board.model.dto.Comment;

public interface CommentService {

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
	List<Comment> select(int boardNo);

	/** 댓글 작성
	 * @param comment
	 * @return
	 */
	int insert(Comment comment);

	/** 댓글 삭제
	 * @param commentNo
	 * @return
	 */
	int delete(int commentNo);

	/** 댓글 수정
	 * @param comments
	 * @return
	 */
	int update(Comment comment);

	/** 검색안한 댓글 전체 조회
	 * @param boardNo
	 * @param cp
	 * @return
	 */
	Map<String, Object> commentAllSelect(int boardNo, int cp);

	/** 검색한 댓글 전체 조회
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> commentSearchSelect(Map<String, Object> paramMap, int cp);

}
