package com.silver.shelter.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.examination.model.dto.Examination;
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

	Examination selectSignUp(Examination exam);

	/** 회원정보 수정 메서드
	 * @param inputMember
	 * @return
	 */
	int updateInfo(Member inputMember);
	
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

	/** 비밀번호 입력이 없는 상태에서의 정보 변경 쿼리
	 * @param inputMember
	 * @return
	 */
	int updateInfo2(Member inputMember);

	
}
