package silverShelter.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import silverShelter.member.model.service.MemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes("loginMember")
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
