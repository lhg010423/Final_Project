package silverShelter.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import silverShelter.admin.model.mapper.AdminMapper;
import silverShelter.member.model.dto.Member;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final AdminMapper mapper;
	
	
	// 회원 목록 조회
	@Override
	public Map<String, Object> memberALlSelect() {
		
		List<Member> memberList = mapper.memberAllSelect();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("memberList", memberList);
		
		return map;
		
	}
	
	

	// 검색한 회원 목록 조회
	@Override
	public Map<String, Object> memberSearchSelect(Map<String, Object> paramMap) {
		
//		int listCount = mapper.getSearchCount(paramMap);
		
		List<Member> boardList = mapper.memberSearchSelect(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		
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
