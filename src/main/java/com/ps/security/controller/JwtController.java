package com.ps.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ps.security.helper.JwtUtil;
import com.ps.security.model.JwtRequest;
import com.ps.security.model.JwtResponse;
import com.ps.security.services.CustomUserDetailService;

@RestController
//@RequestMapping("/token")
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@RequestMapping(value="/token", method= RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		 System.out.println(jwtRequest);
		 
		 try {
			 this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
			 
		 } catch(UsernameNotFoundException e) {
			 e.printStackTrace();
			 throw new Exception("UserName not found");
		 } catch(BadCredentialsException e) {
			 e.printStackTrace();
			 throw new Exception("Bad credentials");
		 }
		 
		 // if authenticate() work, then
		 UserDetails userDetail =this.customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
		    String token = this.jwtUtil.generateToken(userDetail);
		    System.out.println("Generated Token----> " + token);
		    
		    return ResponseEntity.ok(new JwtResponse(token));
	}
	
	
	
}
