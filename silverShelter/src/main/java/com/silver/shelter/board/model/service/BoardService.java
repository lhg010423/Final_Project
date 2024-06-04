package com.silver.shelter.board.model.service;

import java.util.List;
import java.util.Map;

public interface BoardService {

	
	/** 게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

	
	/** 공지게시판 게시글 조회 검색X
	 * @param cp
	 * @return
	 */
//	Map<String, Object> noticeAllSelect(int cp);

	/** 공지게시판 게시글 조회 검색O
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> noticeSearchSelect(Map<String, Object> paramMap, int cp);


}
