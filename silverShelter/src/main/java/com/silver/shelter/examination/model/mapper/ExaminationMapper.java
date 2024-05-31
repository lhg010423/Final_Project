package com.silver.shelter.examination.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.document.model.dto.Document;
import com.silver.shelter.examination.model.dto.Examination;

@Mapper
public interface ExaminationMapper {


	/* 
	 * @param documentSubmisson
	 * */
	int checkApplicantInfo(Examination applicantInfo);

	int insertApplicantInfo(Examination documentSubmisson);

	int insertDocument(List<Document> docList);

}
