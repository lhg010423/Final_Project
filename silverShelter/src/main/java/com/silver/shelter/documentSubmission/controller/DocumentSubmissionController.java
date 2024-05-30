package com.silver.shelter.documentSubmission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.silver.shelter.documentSubmission.model.dto.DocumentSubmisson;
import com.silver.shelter.documentSubmission.model.service.DocumentSubmissionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("documentSubmission")
@Slf4j
@RequiredArgsConstructor
public class DocumentSubmissionController {

	private final DocumentSubmissionService service;
	/** 서류 제출 
	 * @return
	 */
	@GetMapping("examination")
	public String examination() {
		
		return "documentSubmission/examination";
	}
	
	
	@ResponseBody
	@PostMapping("submit")
	public int submitDocument(DocumentSubmisson documentSubmisson) {
		
		int result = service.submitDocument(documentSubmisson);
		
		return 0;
		
	}
}
