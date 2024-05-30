package com.silver.shelter.documentSubmission.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.documentSubmission.model.dto.DocumentSubmisson;

@Mapper
public interface DocumentSubmissonMapper {

	
	/** 서류 저장 mapper
	 * @param documentSubmisson
	 * @return
	 */
	int submitDocument(DocumentSubmisson documentSubmisson);

}
