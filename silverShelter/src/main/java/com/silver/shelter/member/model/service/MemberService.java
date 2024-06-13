package com.silver.shelter.member.model.service;

import java.util.Map;

import com.silver.shelter.examination.model.dto.Examination;
import com.silver.shelter.member.model.dto.Member;

public interface MemberService {

	Member login(Member inputMember);
	
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
	
	String foundId(Member member);
	
	/** 회원 탈퇴
	 * @param map
	 * @param loginMember
	 * @return
	 */
	int secession(Map<String, String> map, Member loginMember);

	/** 비밀번호 변경을 위해 아이디와 비밀번호를 확인하는 메서드
	 * @param memberId
	 * @param memberTel
	 * @return
	 */
	boolean checkIdTel(String memberId, String memberTel);

	/**아이디와 전화번호가 일치하면 비밀번호 변경을 진행하는 메서드
	 * @param newPw
	 * @return
	 */
	boolean updatePw(String memberId, String newPw);

	Examination selectSignUp(Examination exam);

	/** 정보 수정
	 * @param inputMember
	 * @param memberAddress
	 * @return
	 */
	int updateInfo(Member inputMember, String[] memberAddress);
	

}
