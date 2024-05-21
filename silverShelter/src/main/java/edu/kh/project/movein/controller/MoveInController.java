package edu.kh.project.movein.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("moveIn")
public class MoveInController {

	@GetMapping("application")
	public String application() {
		
		return "/moveIn/application";
	}
}
