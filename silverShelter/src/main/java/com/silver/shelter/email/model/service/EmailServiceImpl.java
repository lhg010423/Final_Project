package com.silver.shelter.email.model.service;

import org.springframework.stereotype.Service;

import com.silver.shelter.email.model.mapper.EmailMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

	private final EmailMapper mapper;
}
