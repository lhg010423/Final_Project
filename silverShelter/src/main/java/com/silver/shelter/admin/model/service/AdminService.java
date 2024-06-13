package com.silver.shelter.admin.model.service;

import java.util.Map;

import com.silver.shelter.examination.model.dto.Examination;
import com.silver.shelter.member.model.dto.Member;

public interface AdminService {

	
	/** 회원 목록 조회
	 * @param cp 
	 * @return
	 */
	Map<String, Object> memberAllSelect(int cp);

	/** 
	 * @param paramMap
	 * @param cp 
	 * @return
	 */
	Map<String, Object> memberSearchSelect(Map<String, Object> paramMap, int cp);

	/** 회원 상세조회
	 * @param memberNo
	 * @return
	 */
	Member adminDetailSelect(int memberNo);



	/** 심사 서류 관리 게시글 조회 검색X
	 * @param cp
	 * @return
	 */
	Map<String, Object> examinationAllSelect(int cp);


	/** 심사 서류 관리 게시글 조회 검색O
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> examinationSearchSelect(Map<String, Object> paramMap, int cp);

	/** 심사 서류 관리 상세조회
	 * @param examId
	 * @return
	 */
	Examination examinationDetailSelect(int examId);

	/** 서류 승인 
	 * @param examId
	 * @return
	 */
	int updateAdminDocument(int examId);

	/** 서류 통과 시 이메일 전송 
	 * @param integer
	 * @return
	 */
	String signUpAdminDocument(String HtmlName, int examId);

	
	
	
	/** 요양사 전체 조회
	 * @param cp
	 * @return
	 */
	Map<String, Object> caregiverAllSelect(int cp);

	
	
	
	
	

}