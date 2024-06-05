package com.silver.shelter.member.model.service;

import java.util.Map;

import com.silver.shelter.member.model.dto.Member;

public interface MemberService {

	Member login(Member inputMember);

	String foundId(Member member);

	int secession(Map<String, String> map, Member loginMember);

}
