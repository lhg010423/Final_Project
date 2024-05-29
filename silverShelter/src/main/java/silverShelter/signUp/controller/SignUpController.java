package silverShelter.signUp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("signUp")
public class SignUpController {

	@GetMapping("documentSubmission")
	public String documentSubmission() {
		
		return "signUp/documentSubmission";
	}
	
}
