package silverShelter.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import silverShelter.admin.model.mapper.AdminMapper;
import silverShelter.board.model.dto.Pagination;
import silverShelter.member.model.dto.Member;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final AdminMapper mapper;
	
	
	// 회원 목록 조회 / 검색 X
	@Override
	public Map<String, Object> memberAllSelect(int cp) {
		
		// 탈퇴하지 않은 회원 수 조회
		int memberCount = mapper.memberALlCount();
		
		// memberCount + cp 을 이용해 pagination 생성
		Pagination pagination = new Pagination(cp, memberCount);
		
		// 페이징 처리
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
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
		
		
		Pagination pagination = new Pagination(cp, memberCount);
		
		int limit = pagination.getLimit();
		int offset = (cp -1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		List<Member> boardList = mapper.memberSearchSelect(paramMap, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
	}


	// 회원 상세 조회
	@Override
	public Member adminDetailSelect(Member member) {
		
		Member memberList = mapper.adminDetailSelect(member);
		
		return memberList;
	}
	
	
	
	
	
	
	
	
	

}
