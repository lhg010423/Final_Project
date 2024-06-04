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

	
	/** 삭제되지 않은 공지게시글 전체 수 조회 검색X
	 * @return
	 */
//	int noticeAllCount();

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


}
