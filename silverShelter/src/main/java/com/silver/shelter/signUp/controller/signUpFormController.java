package com.silver.shelter.signUp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.silver.shelter.signUp.model.service.signUpFormService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class signUpFormController {

	public final signUpFormService service;
	
	/** 아이디 중복검사 하는 ajax 메서드
	 * @param memberId
	 * @return
	 */
	@GetMapping("checkId")
	public int checkId (@RequestParam("memberId") String memberId) {
		
		return service.checkId(memberId);
	}
	
}
