package com.ps.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class TestController {
	
	@RequestMapping("/adm")
	public String admin() {
		return "Welcome to spring security admin page1";
	}
	
	/*@RequestMapping("/2")
	public String myAdmin() {
		return "Welcome to spring security admin page2";
	}
*/
}
