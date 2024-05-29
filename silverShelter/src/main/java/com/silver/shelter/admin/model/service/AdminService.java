package com.silver.shelter.admin.model.service;

import java.util.List;
import java.util.Map;

import com.silver.shelter.member.model.dto.Member;

public interface AdminService {

	
	/** 회원 목록 조회
	 * @return
	 */
	Map<String, Object> memberALlSelect();

	/** 
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> memberSearchSelect(Map<String, Object> paramMap);

	/** 회원 상세조회
	 * @param member
	 * @return
	 */
	Member adminDetailSelect(Member member);
	
	
	
	
	
	

}
