package com.silver.shelter.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	Member login(String memberId);

	int foundIdCount(Member member);

	Member foundId(Member member);

}
