package com.silver.shelter.signUp.model.service;
import com.silver.shelter.member.model.dto.Member;
public interface signUpFormService {


	/** 아이디 중복검사하는 ajax 메서드
	 * @param memberId
	 * @return
	 */
	int checkId(String memberId);

	/** 회원 가입 서비스 
	 * @param inputMember
	 * @param memberAddress
	 * @return result
	 */
	int signUp(Member inputMember, String[] memberAddress);

}