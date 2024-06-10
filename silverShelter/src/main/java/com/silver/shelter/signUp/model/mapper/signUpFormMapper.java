package com.silver.shelter.signUp.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface signUpFormMapper {

	/** 아이디 중복검사 mapper
	 * @param memberId
	 * @return count
	 */
	int checkId(String memberId);

	
}
