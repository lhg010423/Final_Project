package com.silver.shelter.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.silver.shelter.member.model.dto.Member;
import com.silver.shelter.myPage.model.service.myPageService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class myPageController {

	public final myPageService service;

	/*
	 * @GetMapping("myPage") 
	 * public String myPageMapping( HttpSession session, 
	 * 								Model model) {
	 * 
	 * Member loginMember = (Member)session.getAttribute("loginMember");
	 * 
	 * if(loginMember == null) {
	 * 
	 * return "redirect:/login"; }
	 * 
	 * model.addAttribute("loginMember",loginMember); return "myPage/myPage"; }
	 */
	
	@GetMapping("myInfo")
	public String myPageMapping() {
		
		return "myPage/myInfo";
	}

	
}
