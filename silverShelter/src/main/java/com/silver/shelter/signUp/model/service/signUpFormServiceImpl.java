package com.silver.shelter.signUp.model.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.silver.shelter.member.model.dto.Member;
import com.silver.shelter.signUp.model.mapper.signUpFormMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class signUpFormServiceImpl implements signUpFormService{
	public final signUpFormMapper mapper;
	public final BCryptPasswordEncoder bcrypt;

	public int checkId(String memberId) {

		return mapper.checkId(memberId);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int signUp(Member inputMember, String[] memberAddress) {
		// 입력되지 않은 주소 -> inputMember.getMemberAddress() => ' , ,  '
		log.info("뭐가넘어오나"+inputMember);
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

}