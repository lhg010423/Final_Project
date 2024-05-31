package com.silver.shelter.examination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.silver.shelter.examination.model.dto.Examination;
import com.silver.shelter.examination.model.service.ExaminationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("examination")
@Slf4j
@RequiredArgsConstructor
public class ExaminationController {

	private final ExaminationService service;
	/** 서류 제출 
	 * @return
	 */
	@GetMapping("examination")
	public String examination() {
		
		return "examination/examination";
	}

	/** 신청자 정보 중복 체크
	 * @param examination
	 * @return
	 */
	@PostMapping("checkApplicantInfo")
	@ResponseBody
	public int checkApplicantInfo(@RequestBody Examination applicantInfo) {
		
		log.info("applicantInfo {}", applicantInfo);
		
		int count = service.checkApplicantInfo(applicantInfo);
		
		// 중복아니면 0, 중복이면 중복된 count
		return count;
	}
	
	/** g히힛
	 * @param documentSubmisson
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@PostMapping("submit")
	public int submitDocument(Examination documentSubmisson) throws Exception {
		
		int result = service.submitDocument(documentSubmisson);
		
		if(result > 0) {
			
			return 1;
		
		} else {
			
			return 0;
		}
		
		
	}
	

}
