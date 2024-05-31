package com.silver.shelter.admin.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.silver.shelter.member.model.dto.Member;

@Mapper
public interface AdminMapper {

	
	/** 탈퇴하지 않은 회원 수 조회
	 * @return
	 */
	int memberAllCount();

	
	/** 회원 목록 조회
	 * @param rowBounds 
	 * @param cp
	 * @return
	 */
	List<Member> memberAllSelect(RowBounds rowBounds);

	/** 검색한 회원 목록 조회
	 * @param paramMap
	 * @param rowBounds 
	 * @param cp
	 * @return
	 */
	List<Member> memberSearchSelect(Map<String, Object> paramMap, RowBounds rowBounds);

	/** 회원 상세 조회
	 * @param memberNo
	 * @return
	 */
	Member adminDetailSelect(int memberNo);


	/** 탈퇴하지 않았고 검색조건에 맞는 회원 수 조회
	 * @param paramMap
	 * @return
	 */
	int memberSearchCount(Map<String, Object> paramMap);


	List<Member> documentSelect();


}
