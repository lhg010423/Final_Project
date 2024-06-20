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

@Slf4j
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
		
		log.info("asdf" + loginMember);
		if(loginMember == null) {
			
			ra.addFlashAttribute("message", "아이디 또는 비밀번호를 확인하세요");
						
			
			return "redirect:/member/login";
		}
		
		model.addAttribute("loginMember",loginMember);
		
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
		
		
		 if (loginMember.getMemberRole() == '1') {
			 
		        return "redirect:/admin/adminMain";
		        
	    } else {
	    	
		        return "redirect:/";
	    }
		
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
	
	
   /** 아이디 찾기 결과
     * @param member
     * @param model
     * @param ra
     * @return
     */
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
	
	/** 비밀번호 찾기 - 아이디와 이메일을 입력받아 서버의 값과 동일한지 조회하는 메서드
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
	public Map<String, Object> updatePw(@RequestBody Map<String, String> req) {
		
	    String memberId = req.get("memberId");
	    String newPw = req.get("newPw");
	    
	    log.info("아이디넘어오고있어? == {}",memberId);
	    
	    log.info("새 비밀번호는? : == {} ",newPw);
	    
	    boolean success = service.updatePw(memberId, newPw);

	    log.info("여기 성공 실패 ? : == {}",success);
	    
	    Map<String, Object> resp = new HashMap<>();
	    resp.put("success", success);

	    return resp;
	}
	
	
	/** 내 정보 - 정보변경 처리 메서드
	 * @return
	 */
	@PostMapping("changeInfo")
	public String updateInfo(@ModelAttribute Member inputMember,
							 @RequestParam("memberAddress") String[] memberAddress,
							 RedirectAttributes ra) {
		
		
		int result = service.updateInfo(inputMember, memberAddress);
		
		String path;
        String message;

        if (result > 0) { // 성공
        	
        	log.info("asdfasdf");
        	
            message = inputMember.getMemberName() + " 님의 정보가 성공적으로 수정되었습니다!";
            path = "/";  // 수정 후 리디렉트할 경로
        } else { // 실패
        	
        	log.info("asdfasdf2222222");
            message = "정보 수정에 실패하였습니다.";
            path = "/myPage/myInfo";  // 수정 실패 시 다시 수정 페이지로 리디렉트
        }

        ra.addFlashAttribute("message", message);
        return "redirect:" + path;
	}
	
	
	
	/** 탈퇴를 위한 정보 컨트롤러 ( 사정상 resources/templates/myPage/secessionForm 을 이쪽으로 땡겨왔음)
	 * @param map
	 * @param loginMember
	 * @param sessionStatus
	 * @return
	 */
	@PostMapping("secession")
	public String secession(@RequestParam Map<String, String> map,
                				@SessionAttribute("loginMember") Member loginMember,
                				SessionStatus sessionStatus,
                				RedirectAttributes ra) {
		log.info("로그인 정보를 주겠어 ?: {}", loginMember);
		
		String memberId = loginMember.getMemberId();
		
		int result = service.secession(map,loginMember);
		
		if (result > 0) {
			
			ra.addFlashAttribute("message", "탈퇴가 완료되었습니다.");
			sessionStatus.setComplete();
			
			return "redirect:/"; // 메인 페이지로 리디렉션
			
		}else {
			ra.addFlashAttribute("message", "비밀번호를 확인해주세요");
			return "redirect:/member/secession"; // 탈퇴 페이지로 다시 리디렉션
		}
		
		
		
	}
}
