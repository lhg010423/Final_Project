package com.silver.shelter.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.silver.shelter.board.model.dto.Board;
import com.silver.shelter.board.model.dto.Pagination;
import com.silver.shelter.board.model.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;
	
	
	
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
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



	/** 공지게시판 게시글 조회 검색O
	 *
	 */
	@Override
	public Map<String, Object> noticeSearchSelect(Map<String, Object> paramMap, int cp) {
		
		int noticeCount = mapper.noticeSearchCount(paramMap);
		
		
		return null;
	}



	
	
	
	
	
}
