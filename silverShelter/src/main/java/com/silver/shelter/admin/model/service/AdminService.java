package com.silver.shelter.admin.model.service;

import java.util.List;
import java.util.Map;

import com.silver.shelter.admin.model.dto.Reservation;
import com.silver.shelter.board.model.dto.Board;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.clubReservation.model.dto.ClubReservation;
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
	Map<String, Object> caregiversAllSelect(int cp);

	/** 요양사 검색한 결과 조회
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> caregiversSearchSelect(Map<String, Object> paramMap, int cp);

	/** 요양사 상세조회
	 * @param caregiversNo
	 * @return
	 */
	CareGiver caregiversDetailSelect(int caregiversNo);

	/** 여가 일정 전체 조회
	 * @param paramMap
	 * @return
	 */
	ClubReservation clubAllSelect(Map<String, Object> paramMap);

	/** 게시글 상세 조회
	 * @param boardNos
	 * @return
	 */
	Board boardDetailSelect(Object boardNo);

	/** 일정 상세조회
	 * @param reservation
	 * @return
	 */
	List<Reservation> reservationAllSelect(ClubReservation reservation);

	/** 일정 조회
	 * @return
	 */
	List<Reservation> getReservedDates();

	
	
	
	
	

}