package com.silver.shelter.signUp.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface signUpFormMapper {

	/** 
	 * @param memberId
	 * @return count
	 */
	int checkId(String memberId);

	
}
