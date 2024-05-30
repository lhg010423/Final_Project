package com.silver.shelter.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.silver.shelter.member.model.dto.Member;
import com.silver.shelter.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
	

	private final MemberMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	@Override
	public Member login(Member inputMember) {
	
		String bcryptPassword = bcrypt.encode(inputMember.getMemberPw() );
		
		Member loginMember = mapper.login(inputMember.getMemberId() );
		
		if( loginMember == null) return null;
		
		if ( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw() )) {
			
			return null;
		}
		
		loginMember.setMemberPw(null);
		
		return loginMember;
}
	
}
