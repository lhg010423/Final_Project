package com.silver.shelter.board.model.service;

import java.util.List;
import java.util.Map;

import com.silver.shelter.board.model.dto.Board;
import com.silver.shelter.board.model.dto.Comment;

public interface BoardService {

	
	/** 게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

	


	
	/** 게시판 게시글 조회 검색X
	 * @param boardCode
	 * @param cp
	 * @return
	 */
	Map<String, Object> boardAllSelect(int boardCode, int cp);

	
	/** 게시판 게시글 조회 검색O
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> boardSearchSelect(Map<String, Object> paramMap, int cp);





	/** 게시글 상세보기
	 * @param map
	 * @return
	 */
	Map<String, Object> boardDetail(Map<String, Object> map);





	/** 조회수 증가
	 * @param boardNo
	 * @return
	 */
	int readCountUpdate(int boardNo);





	/** 게시글 상세조회
	 * @param map
	 * @return
	 */
	Board boardDetailSelect(Map<String, Object> map);





	/** 게시글 수정하기
	 * @param map
	 * @return
	 */
	int boardUpdate(Map<String, Object> map);






	/** 메인페이지 하단 게시글 조회
	 * @return
	 */
	Map<String, Object> mainBoardSelect();

	/** 게시글 삭제하기
	 * @param boardNo
	 * @return
	 */
	int boardDelete(int boardNo);





	/** 댓글 조회
	 * @param boardNo 
	 * @return
	 */
	List<Comment> commentSelect(int boardNo);






}
