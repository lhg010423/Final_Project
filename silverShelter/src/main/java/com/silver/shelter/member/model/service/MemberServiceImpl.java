package com.silver.shelter.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.silver.shelter.member.model.dto.Member;
import com.silver.shelter.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
		
		log.info("bcryptPassword : " + bcryptPassword);
		
		  Member loginMember = mapper.login(inputMember.getMemberId() );
		  
		  if( loginMember == null) return null;
		  
		  
		  // 암호화된 비밀번호와 사용자가 입력한 비밀번호가 일치하면
		  if ( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw() )) {
		  
			  return null; 
		  }
		  
		  
		  
		  loginMember.setMemberPw(null);
		  
		  return loginMember;
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
	public int secession(Map<String, String> map, Member loginMember) {
		
		int memberNo = loginMember.getMemberNo();
		
		String encPw = mapper.findPw(memberNo);
		String pwInput = map.get("pwInput");
		
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
	public boolean updatePw(String memberId, String newPw) {
		
		String encPw = bcrypt.encode(newPw);
		Map<String, String> param = new HashMap<>();
		
		param.put("memberId", memberId);
		param.put("encPw", encPw);
		
		int result = mapper.updatePw(param);
		
		return result > 0;
	}
	
}
