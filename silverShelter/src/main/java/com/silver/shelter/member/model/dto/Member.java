package com.silver.shelter.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberEmail;
	private String memberAddress;
	private String memberTel;
	private String guardianTel;    // 보호자 전화번호
	private String enrollDate;     // 회원 가입일
	private String memberDelFl;    // 회원 탈퇴 여부
	private int memberRole;        // 관리자 계정 판별
	private String caregiversName; // 요양사 이름
	private int roomNo;            // 방 번호
	private String examStatus;     // 심사 승인 여부
	private String examDate;       // 심사 신청일
//	private int examNo;
//	private String caregiversName;
	
	
	
}
