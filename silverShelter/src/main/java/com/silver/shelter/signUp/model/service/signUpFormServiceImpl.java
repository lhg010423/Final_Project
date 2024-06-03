package com.silver.shelter.signUp.model.service;

import org.springframework.stereotype.Service;

import com.silver.shelter.signUp.model.mapper.signUpFormMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class signUpFormServiceImpl implements signUpFormService{

	public final signUpFormMapper mapper;
	
	
	public int checkId(String memberId) {
		
		return mapper.checkId(memberId);
	}

}
