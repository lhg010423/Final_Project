package com.silver.shelter.signUp.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	
	

}
