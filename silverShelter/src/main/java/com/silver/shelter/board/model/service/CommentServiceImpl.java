package com.silver.shelter.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silver.shelter.board.model.dto.Comment;
import com.silver.shelter.board.model.dto.Pagination;
import com.silver.shelter.board.model.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

	
	private final CommentMapper mapper;
	
	// 댓글 목록 조회
	@Override
	public List<Comment> select(int boardNo) {
		return mapper.select(boardNo);
	}

	// 댓글 작성
	@Override
	public int insert(Comment comment) {
		return mapper.insert(comment);
	}

	// 댓글 삭제
	@Override
	public int delete(int commentNo) {
		return mapper.delete(commentNo);
	}

	// 댓글 수정
	@Override
	public int update(Comment comment) {
		return mapper.update(comment);
	}

	// 검색안한 댓글 전체 조회
	@Override
	public Map<String, Object> commentAllSelect(int boardNo, int cp) {
		
		int commentCount = mapper.commentAllCount(boardNo);
		
		System.out.println("commentCount @@@@@@@@@@ "+commentCount);
		
		Pagination pagination = new Pagination(cp, commentCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Comment> commentList = mapper.commentAllSelect(boardNo, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("commentList", commentList);
		map.put("commentCount", commentCount);
		
		return map;
	}

	// 검색한 댓글 전체 조회
	@Override
	public Map<String, Object> commentSearchSelect(Map<String, Object> paramMap, int cp) {
		
		int commentCount = mapper.commentSearchCount(paramMap);
		System.out.println("검색한 결과 개수 : " + commentCount);
		
		System.out.println("댓글 키, 쿼리값 확인용 : "+paramMap);
		
		
		Pagination pagination = new Pagination(cp, commentCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Comment> commentList = mapper.commentSearchSelect(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("commentList", commentList);
		map.put("commentCount", commentCount);
		
		return map;
	}
	
	
	
	
}
