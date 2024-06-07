package com.silver.shelter.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	Member login(String memberId);

	int foundIdCount(Member member);

	Member foundId(Member member);

	String findPw(int memberNo);

	void secession(int memberNo);

	Member checkIdTel(Map<String, String> param);

	/** 비밀번호 업데이트( 변경 ) 하는 메서드
	 * @param encPw
	 * @return
	 */
	int updatePw(Map<String, String> param);

	
}
