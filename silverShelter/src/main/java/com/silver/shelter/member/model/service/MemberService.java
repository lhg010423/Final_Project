package com.silver.shelter.member.model.service;

import com.silver.shelter.member.model.dto.Member;

public interface MemberService {

	Member login(Member inputMember);

	String foundId(Member member);

}
