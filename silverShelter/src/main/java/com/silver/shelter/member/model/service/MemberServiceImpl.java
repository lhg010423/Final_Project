package com.silver.shelter.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.silver.shelter.examination.model.dto.Examination;
import com.silver.shelter.member.model.dto.Member;
import com.silver.shelter.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
	

	private final MemberMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	
	/** 로그인 서비스
	 *
	 */
	@Override
	public Member login(Member inputMember) {
	
		String bcryptPassword = bcrypt.encode(inputMember.getMemberPw() );
		
		//log.info("bcryptPassword : " + bcryptPassword);
		
		  Member loginMember = mapper.login(inputMember.getMemberId() );

			//log.info("1 : " + loginMember);
		  
		  if( loginMember == null) return null;
		  
		  
		  // 암호화된 비밀번호와 사용자가 입력한 비밀번호가 일치하면
		  if ( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw() )) {
		  
			  return null; 
		  }
		  
		  
		  
		  loginMember.setMemberPw(null);
		  
		  return loginMember;
		 }

	public int checkId(String memberId) {
		
		return mapper.checkId(memberId);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int signUp(Member inputMember, String[] memberAddress) {
		// 입력되지 않은 주소 -> inputMember.getMemberAddress() => ' , ,  '
		//log.info("뭐가넘어오나"+inputMember);
		if( !inputMember.getMemberAddress().equals(",,") ) {
			// join ("구분자", 배열) ^^^ 를 사용한 이유는 
			// 주소나 상세주소에 없을것 같은 특수문자를 사용한것임.
			// 나중에 다시 3분할 시 구분자로 이용할 예정임
			String address = String.join("^^^", memberAddress);
			// inputMember주소로 합쳐진 주소 세팅
			inputMember.setMemberAddress(address);
			
		}else {
			
			inputMember.setMemberAddress(null);
		}
		
		// 비밀번호 암호화
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		
		return mapper.signUpForm(inputMember);
	}
	
	// 아이디 찾기 
	@Override
	public String foundId(Member member) {
		int result = mapper.foundIdCount(member);
		
		if (result > 0) {
			
			Member findMember = mapper.foundId(member);
			
			return findMember.getMemberId();
		}else {
			return "not correct";
		}
		
				
	}

	// 회원 탈퇴
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int secession(Map<String, String> map, Member loginMember) {
		
		int memberNo = loginMember.getMemberNo();
		
		String encPw = mapper.findPw(memberNo);
		String pwInput = map.get("memberPw2");
		
		boolean flag = bcrypt.matches(pwInput, encPw);
		
		int result = 0;
		
		if(bcrypt.matches(pwInput, encPw)) {
			mapper.secession(memberNo);
			
			result = 1;
		}
		return result;
	}

	// 아이디, 연락처 조회하기
	@Override
	public boolean checkIdTel(String memberId, String memberTel) {
		Map<String, String> param = new HashMap<>();
		
		param.put("memberId", memberId);
        param.put("memberTel", memberTel);
		
		Member member = mapper.checkIdTel(param);
		
		return member != null;
	}

	
	// 비밀번호 업데이트 하기
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean updatePw(String memberId, String newPw) {
		
		String encPw = bcrypt.encode(newPw);
		Map<String, String> param = new HashMap<>();
		
		param.put("memberId", memberId);
		param.put("encPw", encPw);
		
		log.info("비밀번호는 ? == {}",encPw);
		
		int result = mapper.updatePw(param);
		
		log.info("1이 들어와야함 == {}", result);
		return result > 0;
	}

	@Override
	public Examination selectSignUp(Examination exam) {
		return mapper.selectSignUp(exam);
	}
	
	// 정보 수정 메서드
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateInfo(Member inputMember, String[] memberAddress) {
		
		
		log.info("aaaa={}", inputMember.getMemberId());
		
		if (inputMember.getMemberPw() != null && !inputMember.getMemberPw().isEmpty() ) {
			String encPw = bcrypt.encode(inputMember.getMemberPw());
			inputMember.setMemberPw(encPw);
		}
		
		// 주소 관련
		if(memberAddress !=null && !String.join(",", memberAddress).equals(",,,")) {
			
			String memAddr = String.join("^^^", inputMember.getMemberAddress());
			inputMember.setMemberAddress(memAddr);
			
		}else {
			inputMember.setMemberAddress(null);
		}
		int result;
		if (inputMember.getMemberPw() != null && !inputMember.getMemberPw().isEmpty() ) {
			result = mapper.updateInfo(inputMember);
		} else {
			result = mapper.updateInfo2(inputMember);
		}
		
		
		return result;
	}
}
