package com.silver.shelter.examination.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.document.model.dto.Document;
import com.silver.shelter.examination.model.dto.Examination;

@Mapper
public interface ExaminationMapper {


	/* 중복 신청 확인 
	 * @param documentSubmisson
	 * */
	int checkApplicantInfo(Examination applicantInfo);

	
	/** 심사 테이블
	 * @param documentSubmisson
	 * @return
	 */
	int insertApplicantInfo(Examination documentSubmisson);

	int insertDocument(List<Document> docList);

}
