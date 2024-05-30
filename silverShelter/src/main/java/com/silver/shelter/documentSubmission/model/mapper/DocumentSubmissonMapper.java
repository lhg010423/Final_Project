package com.silver.shelter.documentSubmission.model.mapper;

import com.silver.shelter.documentSubmission.model.dto.DocumentSubmisson;

public interface DocumentSubmissonMapper {

	
	/** 서류 저장 mapper
	 * @param documentSubmisson
	 * @return
	 */
	int submitDocument(DocumentSubmisson documentSubmisson);

}
