package com.silver.shelter.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.silver.shelter.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	
	/** 게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

	
	/** 삭제되지 않은 전체 게시글 수 조회
	 * @param boardCode
	 * @return
	 */
	int boardAllCount(int boardCode);

	
	/** 삭제되지 않은 전체 게시글 조회
	 * @param boardCode
	 * @param rowBounds
	 * @return
	 */
	List<Board> boardAllSelect(int boardCode, RowBounds rowBounds);
	
	
	
	/** 삭제되지 않은 검색한 게시글 수 조회
	 * @param paramMap
	 * @return
	 */
	int boardSearchCount(Map<String, Object> paramMap);

	
	
	/** 삭제되지 않은 검색한 게시글 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Board> boardSearchSelect(Map<String, Object> paramMap, RowBounds rowBounds);
	
	
	
	
	
	
	
	
	
	

	/** 삭제되지 않은 공지게시글 전체 조회 검색X
	 * @param rowBounds
	 * @return
	 */
	List<Board> noticeAllSelect(RowBounds rowBounds);

	/** 삭제되지 않은 공지게시글 전체 수 조회 검색O
	 * @param paramMap
	 * @return
	 */
	int noticeSearchCount(Map<String, Object> paramMap);


	/** 게시글 상세보기
	 * @param map
	 * @return
	 */
	Board boardDetail(Map<String, Object> map);

	
	/** 게시글 상세조회
	 * @param map
	 * @return
	 */
	Board boardDetailSelect(Map<String, Object> map);

	
	/** 조회수 1 증가
	 * @param boardNo
	 * @return
	 */
	int readCountUpdate(int boardNo);


	/** 조회수 조회
	 * @param boardNo
	 * @return
	 */
	int readCountSelect(int boardNo);


	/** 게시글 수정하기
	 * @param map
	 * @return
	 */
	int boardUpdate(Map<String, Object> map);






	





}
