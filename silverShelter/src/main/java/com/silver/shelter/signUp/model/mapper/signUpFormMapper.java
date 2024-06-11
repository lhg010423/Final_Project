package com.silver.shelter.signUp.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.member.model.dto.Member;

@Mapper
public interface signUpFormMapper {

	/** 아이디 중복검사 mapper
	 * @param memberId
	 * @return count
	 */
	int checkId(String memberId);

	/** 회원가입 sql 
	 * @param inputMember
	 * @return result
	 */
	int signUpForm(Member inputMember);

	
}
