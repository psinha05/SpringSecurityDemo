package com.ps.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class HomeController {
	
	
	@RequestMapping("/home")
	public String home() {
		return "this is home page";
	}
	
	@RequestMapping("/about")
	public String login() {
		return "this is about page";
	}

	
	@RequestMapping("/register")
	public String register() {
		return "this is register page";
	}


}
