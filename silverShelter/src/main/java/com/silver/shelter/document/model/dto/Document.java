package com.silver.shelter.document.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {

	
	private int documentNo;
	private String documentOriginalName;
	private String documentPath;
	private int docTypeCode;
	private int examId;
	
	

}
