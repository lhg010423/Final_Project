package com.silver.shelter.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
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
@SessionAttributes({"loginMember"})
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
	
	@GetMapping("logout")
	public String logout(SessionStatus status,
						 RedirectAttributes ra){
	
		status.setComplete();
		
		ra.addFlashAttribute("message", "로그아웃되었습니다");
		
		return "redirect:/";
	}
	
	
	/** 로그인 메서드
	 * @param inputMember
	 * @param ra
	 * @param model
	 * @param saveId
	 * @param resp
	 * @return
	 */
	@PostMapping("login")
	public String login(@ModelAttribute Member inputMember,
						RedirectAttributes ra,
						Model model,
						@RequestParam(value="saveId",required= false) String saveId,
						HttpServletResponse resp) {
		
		Member loginMember = service.login(inputMember);
		
		
		if(loginMember == null) {
			
			ra.addFlashAttribute("message", "아이디 또는 비밀번호를 확인하세요");
						
			
			return "redirect:/member/login";
		}
		
		if(loginMember != null) {
			//log.debug("adsfasdfasdfasdf");
			
			model.addAttribute("loginMember", loginMember);
		
			Cookie cookie = new Cookie("saveId" , loginMember.getMemberId() );
			
			cookie.setPath("/");
			
			// 만료기간
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
	
	/** 아이디 찾기 화면으로 가는 메서드
	 * @return
	 */
	@GetMapping("foundId")
	public String foundId() {
		
		return "/member/foundId";
	}
	
	
   @PostMapping("idResult") 
   public String foundId(@ModelAttribute Member member,
		   				 Model model,
		   				 RedirectAttributes ra) {
	  
	   String result = service.foundId(member);
	   String message = null;
	   
	   if(result.equals("not correct")) {
		   message = "일치하는 사용자의 정보가 없습니다. 입력 정보를 확인하세요";
		   ra.addFlashAttribute("message", message);
		   return "redirect:/member/foundId";
		   
	   }else {
		   model.addAttribute("memberId", result);
		   return "/member/idResult";
	   }
	   
		   
	 }
	 
	
	@GetMapping("foundPw")
	public String foundPw() {
		
		return "member/foundPw";
	}
	

	
	
	
}
