package com.silver.shelter.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.silver.shelter.examination.model.dto.Examination;
import com.silver.shelter.member.model.dto.Member;
import com.silver.shelter.member.model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
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
	 
	
	/** 비밀번호 찾기 버튼 클릭시 랜더링하는 메서드
	 * @return
	 */
	@GetMapping("foundPw")
	public String foundPw() {
		
		return "member/foundPw";
	}
	
	//"http://loscalhost/member/signUp/411104RSORMD!@$/17"
	@GetMapping("signUp/{path:[A-Za-z0-9!@$^]+}/{examId:[0-9]+}")
	public String signUp(@PathVariable("path")String path,
						 @PathVariable("examId")int inputExamId,
						 Model model) {
		
		// examId 가지고 -> S > M -> DB
		Examination exam = new Examination();
		
		exam.setExamId(inputExamId);
		
		log.info("examID는???"+exam.getExamId());
		
		exam = service.selectSignUp(exam);
		// examId일치하는 행의 이름/이메일/전화번호
		model.addAttribute("exam",exam);
		log.info("잘 넘어오니?",exam.getExamName());
		// request Scope에 넣기

		
		return "member/signUp";
	}
	
	/** 비밀번호 찾기 브라우저에서 아이디와 이메일을 입력받아 서버의 값과 동일한지 조회하는 메서드
	 * @return
	 */
	@PostMapping("checkIdTel")
	@ResponseBody
	public String checkIdTel(@RequestBody Member member) {
		String memberId = member.getMemberId();
		String memberTel = member.getMemberTel();
		
		boolean isVaild = service.checkIdTel(memberId, memberTel);
		
		if(isVaild) {
			return "success";
		}else {
			return "failure";
		}
			
	}
	
	/** 아이디와 전화번호가 일치하면 비밀번호 변경을 진행하는 메서드
	 * @param req
	 * @return
	 */
	@PostMapping("updatePw")
	@ResponseBody
	public Map<String, Object> updatePw(@RequestBody Map<String, String> req){
	    String memberId = req.get("memberId");
	    String newPw = req.get("newPw");

	    boolean success = service.updatePw(memberId, newPw);

	    Map<String, Object> resp = new HashMap<>();
	    resp.put("success", success);

	    return resp;
	}
	
	/** 탈퇴를 위한 정보 컨트롤러 ( 사정상 resources/templates/myPage/secessionForm 을 이쪽으로 땡겨왔음)
	 * @param map
	 * @param loginMember
	 * @param sessionStatus
	 * @return
	 */
	@PostMapping("secession")
	@ResponseBody
	public int secession(@RequestBody Map<String, String> map,
						 @SessionAttribute("loginMember") Member loginMember,
						 SessionStatus sessionStatus,
						 RedirectAttributes ra
						  ) {
		
		String memberId = loginMember.getMemberId();
		
		int result = service.secession(map,loginMember);
		
		if (result > 0) {
			
			ra.addFlashAttribute("message", "탈퇴가 완료되었습니다.");
			sessionStatus.setComplete();
			
		}else {
			ra.addFlashAttribute("message", "비밀번호를 확인해주세요");
		}
		
		return result;
		
	}
}
