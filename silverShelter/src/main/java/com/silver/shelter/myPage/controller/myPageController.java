package com.silver.shelter.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.silver.shelter.member.model.dto.Member;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("myPage")
public class myPageController {


	/*
	 * @GetMapping("myPage") public String myPageMapping( HttpSession session, Model
	 * model) {
	 * 
	 * Member loginMember = (Member)session.getAttribute("loginMember");
	 * 
	 * if(loginMember == null) {
	 * 
	 * return "redirect:/login"; }
	 * 
	 * model.addAttribute("loginMember",loginMember); return "myPage/myPage"; }
	 */
	
	@GetMapping("myPage")
	public String myPageMapping() {
		
		return "myPage/myPage";
	}
}
