package com.silver.shelter.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.silver.shelter.member.model.dto.Member;
import com.silver.shelter.member.model.service.MemberService;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes("{loginMember}")
@RequestMapping("member")
public class MemberController {

	private final MemberService service;
	
	
	
	/** 로그인 페이지로 이동
	 * @return
	 */
	@GetMapping("login")
	public String login() {
		return "/member/login";
	}
	
	@PostMapping("login")
	public String login(@ModelAttribute Member inputMember,
						RedirectAttributes ra,
						Model model,
						@RequestParam(value="saveId",required= false) String saveId,
						HttpServletResponse resp) {
		
		Member loginMember = service.login(inputMember);
		
		
		if(loginMember == null) {
			
			ra.addFlashAttribute("message", "아이디 또는 비밀번호를 확인해주세요");
		}else {
			
			
		}
		
		if(loginMember != null) {
			
			model.addAttribute("loginMember", loginMember);
		
			Cookie cookie = new Cookie("saveId" , loginMember.getMemberId() );
			
			cookie.setPath("/");
			
			if (saveId != null) {
				
				cookie.setMaxAge( 60* 60 * 24 * 30);
			}else {
				cookie.setMaxAge(0);
			}
			
			resp.addCookie(cookie);
		}
		
		
		return "redirect:/";
	}
	
	
	@GetMapping("Introduction")
	public String Introduction() {
		
		return "/member/Introduction";
	}
	
	@GetMapping("foundId")
	public String foundId() {
		
		return "member/foundId";
	}
	
	@GetMapping("foundPw")
	public String foundPw() {
		
		return "member/foundPw";
	}
	
//	public String login(@ModelAttribute Member inputMember,
//						RedirectAttributes ra,
//						Model model,
//						HttpServletResponse resp
//			) {
//		
//		
//		return "";
//	}
	
	
	
}
