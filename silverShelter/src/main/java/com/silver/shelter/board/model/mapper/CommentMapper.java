package com.silver.shelter.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.silver.shelter.board.model.dto.Comment;

@Mapper
public interface CommentMapper {

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
	 * @param comment
	 * @return
	 */
	int update(Comment comment);

	/** 검색안한 댓글 전체 수 조회
	 * @param boardNo
	 * @return
	 */
	int commentAllCount(int boardNo);

	/** 검색 안한 댓글 전체 조회
	 * @param boardNo
	 * @param rowBounds
	 * @return
	 */
	List<Comment> commentAllSelect(int boardNo, RowBounds rowBounds);

	/** 검색한 댓글 전체 수 조회
	 * @param paramMap
	 * @return
	 */
	int commentSearchCount(Map<String, Object> paramMap);

	/** 검색한 댓글 전체 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Comment> commentSearchSelect(Map<String, Object> paramMap, RowBounds rowBounds);

}
