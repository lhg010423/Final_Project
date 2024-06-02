package com.silver.shelter.email.controller;

import org.springframework.stereotype.Controller;

import com.silver.shelter.email.model.service.EmailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmailController {

	private final EmailService service;
	
	
}
