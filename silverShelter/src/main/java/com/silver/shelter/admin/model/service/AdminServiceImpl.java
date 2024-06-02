package com.silver.shelter.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.silver.shelter.admin.model.mapper.AdminMapper;
import com.silver.shelter.board.model.dto.Pagination;
import com.silver.shelter.examination.model.dto.Examination;
import com.silver.shelter.member.model.dto.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final AdminMapper mapper;
	
	
	// 회원 목록 조회 / 검색 X
	@Override
	public Map<String, Object> memberAllSelect(int cp) {
		
		// 탈퇴하지 않은 회원 수 조회
		int memberCount = mapper.memberAllCount();
		
		// memberCount + cp 을 이용해 pagination 생성
		Pagination pagination = new Pagination(cp, memberCount);
		
		// 페이징 처리
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		// 회원 서류 관리 페이지에서 Examination테이블의 값들을 축력함
		List<Member> memberList = mapper.memberAllSelect(rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("memberList", memberList);
		
		return map;
	}
	
	
	// 서비스 키 발급, 오픈api reponse 할때 xml json 타입 구분
	// 자바스크립트로 하는거만 배웠는데 자바로 되어있는거 한번보기
	// 공공데이터에서 맞는 api를 검색해서 맞는거를 찾아야함 주소를 사용해야해서

	// 회원 목록 조회 / 검색 O
	@Override
	public Map<String, Object> memberSearchSelect(Map<String, Object> paramMap, int cp) {
		
		// 탈퇴하지 않았고 검색조건에 맞는 회원 수 조회하기
		int memberCount = mapper.memberSearchCount(paramMap);
		
		log.info("memberCount {}" + memberCount);
		
		
		Pagination pagination = new Pagination(cp, memberCount);
		
		int limit = pagination.getLimit();
		int offset = (cp -1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		List<Member> memberList = mapper.memberSearchSelect(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("memberList", memberList);
		
		return map;
	}


	// 회원 상세 조회
	@Override
	public Member adminDetailSelect(int memberNo) {
		
		
		
		return mapper.adminDetailSelect(memberNo);
	}
	
	
	
	

	/** 심사 서류 관리 게시판 조회 검색X
	 *
	 */
	@Override
	public Map<String, Object> examinationAllSelect(int cp) {
		
		// 전체 서류 수
		int examinationCount = mapper.examinationAllCount();
		
		// memberCount + cp 을 이용해 pagination 생성
		Pagination pagination = new Pagination(cp, examinationCount);
		
		// 페이징 처리
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		// 심사 서류 관리 페이지에서 Examination테이블의 값들을 축력함
		List<Examination> examinationList = mapper.examinationAllSelect(rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("examinationList", examinationList);

		return map;
	}


	/** 심사 서류 관리 게시판 조회 검색O
	 *
	 */
	@Override
	public Map<String, Object> examinationSearchSelect(Map<String, Object> paramMap, int cp) {
		
		// 검색한 서류 수
		int examinationCount = mapper.examinationSearchCount(paramMap);
		
		Pagination pagination = new Pagination(cp, examinationCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Examination> examinationList = mapper.examinationSearchSelect(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("examinationList", examinationList);
		
		
		return map;
	}


	@Override
	public Examination examinationDetailSelect(int examId) {
		return mapper.examinationDetailSelect(examId);
	}



	
	
	
	
	
	
	
	
	

}
