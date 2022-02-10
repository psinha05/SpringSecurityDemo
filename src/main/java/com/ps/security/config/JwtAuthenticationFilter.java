package com.ps.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ps.security.helper.JwtUtil;
import com.ps.security.services.CustomUserDetailService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		 String requestTokenHeader= request.getHeader("Authorization");
		                            System.out.println(request.getHeader("Authorization"));
		 System.out.println("My Authorizationnnnnnnnnnnnnnn" + requestTokenHeader);
		  String username=null;
		  String jwtToken = null;
		  
		  System.out.println("***********TESTING Here*********");
		  // need to get token exclude Bearer(which has 7 along with space)
		  if(requestTokenHeader !=null && requestTokenHeader.startsWith("Bearer "))
		      {
			    jwtToken=requestTokenHeader.substring(7);
			    System.out.println("***********jwtToken*********" + jwtToken);
			  
			  try {
				  username=this.jwtUtil.getUsernameFromToken(jwtToken);
				  System.out.println("***********username*********" + username);
				  
			  } catch(Exception e) {
				  e.printStackTrace();
			  }
			  
			  
			  UserDetails userDetail=this.customUserDetailService.loadUserByUsername(username);
			  System.out.println("To get userDetail " + userDetail);
			  
			  // SecurityContxt is an Interface with min security information with current associated thread
			  // SecurityContextHolder`- class associates with security info with current associated thread
			  
			  if(username !=null && SecurityContextHolder.getContext().getAuthentication()== null) {
				  UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
				  
				  userNamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				  System.out.println("################" + userNamePasswordAuthenticationToken);
				  SecurityContextHolder.getContext().setAuthentication(userNamePasswordAuthenticationToken);
				  
			  } else {
				  System.out.println("Token is not validated");
			  }
			  
			 //filterChain.doFilter(request, response);
		  }
		  
		  filterChain.doFilter(request, response);
		
	}
	
	

}
