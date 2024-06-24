package com.silver.shelter.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.silver.shelter.board.model.dto.Board;
import com.silver.shelter.board.model.dto.Comment;

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



	


	/** 최신 공지게시글 1개 조회
	 * @return
	 */
	Board mainBoardSelect(int boardCode);





	/** 게시글 삭제하기
	 * @param boardNo
	 * @return
	 */
	int boardDelete(int boardNo);


	/** 댓글 전체 조회
	 * @return
	 */
	List<Comment> commentAllSelect(long boardNo);


	/** 좋아요 해제
	 * @param map
	 * @return
	 */
	int boardLikeDelete(Map<String, Integer> map);


	/** 좋아요 체크
	 * @param map
	 * @return
	 */
	int boardLikeInsert(Map<String, Integer> map);


	/** 좋아요 수 조회
	 * @param integer
	 * @return
	 */
	int likeCountSelect(Integer integer);


	/** 게시글 작성
	 * @param inputBoard
	 * @return
	 */
	int boardInsert(Board inputBoard);


	





	





}
