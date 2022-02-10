package com.ps.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtTestController {

	
	@GetMapping("/jwt")
	 public String testJwt() {
		 String text=" This is private message space";
		       return text;
	 }
}
