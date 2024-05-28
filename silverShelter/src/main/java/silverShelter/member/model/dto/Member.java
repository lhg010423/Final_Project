package silverShelter.member.model.dto;

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
	private String enrollDate;   // 회원 가입일
	private String memberDelFl;  // 회원 탈퇴 여부
	private int memberRole;      // 관리자 계정 판별
//	private String managerCheck; // 관계자 확인
	
	
	
}
