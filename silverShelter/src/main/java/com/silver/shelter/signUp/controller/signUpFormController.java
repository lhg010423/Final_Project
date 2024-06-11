package com.silver.shelter.signUp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.silver.shelter.member.model.dto.Member;
import com.silver.shelter.signUp.model.service.signUpFormService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@RequestMapping("member")
@Slf4j
public class signUpFormController {

	public final signUpFormService service;
	
	/** 아이디 중복검사 하는 ajax 메서드
	 * @param memberId
	 * @return
	 */
	@GetMapping("/checkId")
    public ResponseEntity<Integer> checkId(@RequestParam("memberId") String memberId) {
        int result = service.checkId(memberId);
        return ResponseEntity.ok(result);
    }
	
	/** 회원 가입 컨트롤러
	 * @param inputMember
	 * @param memberAddress
	 * @param ra
	 * @return
	 */
	@PostMapping("signUpForm")
	public String signUp(@ModelAttribute Member inputMember,
						 @RequestParam("memberAddress") String [] memberAddress,
						 @RequestParam("examId") int examId,
						 RedirectAttributes ra) {
		log.debug("{}=", memberAddress[0]);
		log.debug("{}=", memberAddress[1]);
		log.debug("{}=", memberAddress[2]);
		
		int result = service.signUp(inputMember, memberAddress);
		
		inputMember.setExamId(examId);
		
		log.info("이제 잘 넘어오겠지? : "+inputMember);
		String path = null;
		String message = null;
		
		
		
		if (result > 0) { // 성공
			message = inputMember.getMemberName()+ " 님의 가입을 환영합니다!";
			path = "/";
			
		}else { // 실패
			message = "회원 가입에 실패하였습니다";
			path = "signUp";
		}
		
		ra.addFlashAttribute("message" ,message);
		
		return "redirect:" + path;
		
	}
}
