package com.silver.shelter.member.model.service;

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
	
}
