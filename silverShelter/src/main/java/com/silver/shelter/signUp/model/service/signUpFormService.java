package com.silver.shelter.signUp.model.service;

public interface signUpFormService {

	
	/** 아이디 중복검사하는 ajax 메서드
	 * @param memberId
	 * @return
	 */
	int checkId(String memberId);

}
