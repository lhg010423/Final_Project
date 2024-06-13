package com.silver.shelter.admin.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.examination.model.dto.Examination;
import com.silver.shelter.member.model.dto.Member;

@Mapper
public interface AdminMapper {

	
	/** 탈퇴하지 않은 회원 수 조회
	 * @return
	 */
	int memberAllCount();

	
	/** 회원 목록 조회
	 * @param rowBounds 
	 * @param cp
	 * @return
	 */
	List<Member> memberAllSelect(RowBounds rowBounds);

	/** 검색한 회원 목록 조회
	 * @param paramMap
	 * @param rowBounds 
	 * @param cp
	 * @return
	 */
	List<Member> memberSearchSelect(Map<String, Object> paramMap, RowBounds rowBounds);

	/** 회원 상세 조회
	 * @param memberNo
	 * @return
	 */
	Member adminDetailSelect(int memberNo);


	/** 탈퇴하지 않았고 검색조건에 맞는 회원 수 조회
	 * @param paramMap
	 * @return
	 */
	int memberSearchCount(Map<String, Object> paramMap);


	

	/** 심사 서류 관리 페이지 게시글 수 조회X
	 * @return
	 */
	int examinationAllCount();

	/** 심사 서류 관리 페이지에서 조회할 게시글 검색X
	 * @return
	 */
	List<Examination> examinationAllSelect(RowBounds rowBounds);


	/** 심사 서류 관리 페이지 게시글 수 조회O
	 * @param paramMap
	 * @return
	 */
	int examinationSearchCount(Map<String, Object> paramMap);


	/** 심서 서류 관리 페이지에서 조회할 게시글 검색O
	 * @param paramMap 
	 * @param rowBounds
	 * @return
	 */
	List<Examination> examinationSearchSelect(Map<String, Object> paramMap, RowBounds rowBounds);


	/** 심서 서류 관리 상세 페이지
	 * @param examId
	 * @return
	 */
	
	Examination examinationDetailSelect(int examId);


	/** 서류 승인 
	 * @param examId
	 * @return
	 */
	int updateAdminDocument(int examId);


	/** 요양사 전체 수 조회
	 * @return
	 */
	int caregiversAllCount();


	/** 요양사 전체 조회
	 * @param rowBounds
	 * @return
	 */
	List<CareGiver> caregiversAllSelect(RowBounds rowBounds);


	/** 요양사 검색한 결과 수 조회
	 * @param paramMap
	 * @return
	 */
	int caregiversSearchCount(Map<String, Object> paramMap);


	List<CareGiver> caregiversSearchSelect(Map<String, Object> paramMap, RowBounds rowBounds);


	






}
