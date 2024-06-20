package com.silver.shelter.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.silver.shelter.board.model.dto.Board;
import com.silver.shelter.board.model.dto.Comment;
import com.silver.shelter.board.model.dto.Pagination;
import com.silver.shelter.board.model.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;
	
	
	
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	}
	
	
	

	/** 게시판 게시글 조회 검색X
	 *
	 */
	@Override
	public Map<String, Object> boardAllSelect(int boardCode, int cp) {
		
		// 삭제되지 않은 게시글 수 조회
		int boardCount = mapper.boardAllCount(boardCode);
		
		
		// noticeCount와 cp를 이용해 Pagination 객체 생성
		// cp는 현재 페이지 번호, noticeCount는 전체 게시글 수를 나타냅니다.
		Pagination pagination = new Pagination(cp, boardCount);

		// 페이징 처리를 위해 한 페이지에 보여줄 게시글 수(limit)와
		// 현재 페이지에서 보여줄 게시글 목록의 시작 위치(offset) 계산
		// limit은 한 페이지에 보여줄 게시글 수입니다.
		int limit = pagination.getLimit();

		// offset은 현재 페이지에서 보여줄 게시글 목록의 시작 위치를 계산합니다.
		// 예를 들어, 현재 페이지(cp)가 2이고, 한 페이지에 10개의 게시글을 보여줄 경우,
		// offset은 (2 - 1) * 10 = 10이 됩니다. 즉, 11번째 게시글부터 보여줍니다.
		// -1하는 이유는 현재페이지 시작이 1인데 이걸 인덱스로 사용하기 위해 -1을 함
		int offset = (cp - 1) * limit;

		// MyBatis의 RowBounds 객체를 생성하여 페이징 처리에 필요한 offset과 limit 설정
		// RowBounds는 MyBatis에서 페이징 처리를 위해 사용하는 클래스입니다.
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.boardAllSelect(boardCode, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		System.err.println("boarList@@@@  ServiceImpl" + map.get("boardList"));
		
		return map;
	}
	
	
	/** 게시판 게시글 조회 검색O
	 *
	 */
	@Override
	public Map<String, Object> boardSearchSelect(Map<String, Object> paramMap, int cp) {
		
		// 검색한 게시글 수 조회
		int boardCount = mapper.boardSearchCount(paramMap);
		log.info("boardCount {}", boardCount);
		
		Pagination pagination = new Pagination(cp, boardCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		log.info("paramMap {}", paramMap);
		List<Board> boardList = mapper.boardSearchSelect(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		//System.out.println("key값 : " + paramMap.get("key"));
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
	}




	/** 게시글 상세보기
	 *
	 */
	@Override
	public Map<String, Object> boardDetail(Map<String, Object> map) {
		
		Map<String, Object> result = new HashMap<>();
		
		Board boardDetail = mapper.boardDetail(map);
		
		// 수정 및 삭제 권한 확인
	    boolean canModify = false;
	    boolean canDelete = false;
		
	    // 로그인한 회원 == 게시글 작성자 || 관리자계정
	    if(map.get("memberNo") != null) {
	    	
	    	if((int)map.get("memberNo") == boardDetail.getMemberNo() || (int)map.get("memberNo") == 1 ) {
	    		canModify = true;
	    		canDelete = true;
	    	}
	    }
	    
	    result.put("boardList", boardDetail);
	    result.put("canModify", canModify);
	    result.put("canDelete", canDelete);
	    
	    
		return result;
	}




	/** 조회수 증가
	 *
	 */
	@Override
	public int readCountUpdate(int boardNo) {

		// 조회수 1 증가
		int result = mapper.readCountUpdate(boardNo);
		
		// 현재 조회 수 조회
		if(result > 0) {
			return mapper.readCountSelect(boardNo);
		}
		
		return -1;
	}




	/** 게시글 상세조회
	 *
	 */
	@Override
	public Board boardDetailSelect(Map<String, Object> map) {
		return mapper.boardDetailSelect(map);
	}




	@Override
	public int boardUpdate(Map<String, Object> map) {
		return mapper.boardUpdate(map);
	}





	/** 메인페이지 하단 게시글 조회
	 *
	 */
	@Override
	public Map<String, Object> mainBoardSelect() {
		
		Map<String, Object> map = new HashMap<>();
		
		
		map.put("notice", getMainBoard(1));
		map.put("free", getMainBoard(2));
		map.put("inquiry", getMainBoard(3));
		
		return map;
  }
    
  private Board getMainBoard(int boardCode) {
		return mapper.mainBoardSelect(boardCode);
	}
  
	/** 게시글 삭제하기
	 *
	 */
	@Override
	public int boardDelete(int boardNo) {
		return mapper.boardDelete(boardNo);
	}




	/** 댓글 조회
	 *
	 */
	@Override
	public List<Comment> commentSelect(int boardNo) {
		
		// 댓글 전체 조회
		List<Comment> comments = mapper.commentAllSelect(boardNo);
		
		// 결과값 전달용 List
		List<Comment> commentList = new ArrayList<>();
		
		// 모든 댓글을 빠르게 찾을 수 있도록 저장할 map
		Map<Long, Comment> commentMap = new HashMap<>();
		// Map<commentNo, comment>
		
		
		// 모든 댓글을 가져오면서 댓글 번호로 map에 저장
		for(Comment comment : comments) {
			commentMap.put( comment.getCommentNo(), comment);
			
		}
		
		
		// 다시 모든 댓글을 순회하면서 부모-자식 관계 설정하기
		for(Comment comment : comments) {
			
			// 대댓글인 경우 부모 댓글의 boardNo를 확인하여 부모 댓글에 추가
			if(comment.getParentCommentNo() > 0) {
			
		
				// 부모 댓글을 map에서 찾아서 가져오기
				Comment parentComment = commentMap.get(comment.getParentCommentNo());
				
				// 부모 댓글이 있을 때
				if(parentComment != null) {
					
					// 부모 댓글에 현재 댓글을 자식 댓글로 추가하기
					parentComment.addChildComment(comment);
					
				}
				
			} else {
				// 최상위 댓글은 리스트에 추가
				commentList.add(comment);
				
			}
		}
		
		System.out.println("commentList 확인용 : " + commentList);
		log.info("commentList {}", commentList);
		
		return commentList;
	}
	
	
	
	
	
	
	
	
	
	/** 공지게시판 게시글 조회 검색X
	 *
	 */
//	@Override
//	public Map<String, Object> noticeAllSelect(int cp) {
//		
//		// 삭제되지 않은 게시글 수 조회
//		int noticeCount = mapper.noticeAllCount();
//		
//		// noticeCount와 cp를 이용해 Pagination 객체 생성
//		// cp는 현재 페이지 번호, noticeCount는 전체 게시글 수를 나타냅니다.
//		Pagination pagination = new Pagination(cp, noticeCount);
//
//		// 페이징 처리를 위해 한 페이지에 보여줄 게시글 수(limit)와
//		// 현재 페이지에서 보여줄 게시글 목록의 시작 위치(offset) 계산
//		// limit은 한 페이지에 보여줄 게시글 수입니다.
//		int limit = pagination.getLimit();
//
//		// offset은 현재 페이지에서 보여줄 게시글 목록의 시작 위치를 계산합니다.
//		// 예를 들어, 현재 페이지(cp)가 2이고, 한 페이지에 10개의 게시글을 보여줄 경우,
//		// offset은 (2 - 1) * 10 = 10이 됩니다. 즉, 11번째 게시글부터 보여줍니다.
//		// -1하는 이유는 현재페이지 시작이 1인데 이걸 인덱스로 사용하기 위해 -1을 함
//		int offset = (cp - 1) * limit;
//
//		// MyBatis의 RowBounds 객체를 생성하여 페이징 처리에 필요한 offset과 limit 설정
//		// RowBounds는 MyBatis에서 페이징 처리를 위해 사용하는 클래스입니다.
//		RowBounds rowBounds = new RowBounds(offset, limit);
//		
//		// 공지게시글 테이블 <td>부분에 사용할 값들 가져오기
//		List<Board> boardList = mapper.noticeAllSelect(rowBounds);
//		
//		Map<String, Object> map = new HashMap<>();
//		
//		map.put("pagination", pagination);
//		map.put("boardList", boardList);
//		
//		return map;
//	}












	
	
	
	
	
}
