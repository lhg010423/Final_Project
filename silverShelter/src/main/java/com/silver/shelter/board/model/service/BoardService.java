package com.silver.shelter.board.model.service;

import java.util.List;
import java.util.Map;

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





}
