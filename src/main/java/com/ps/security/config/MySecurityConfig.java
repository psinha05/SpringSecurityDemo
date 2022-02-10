package com.ps.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	
	// this will throw expection as we are not using any password encoder
	// for handle the exception, using the PasswordEncoder
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.inMemoryAuthentication()
		      .withUser("max")
		      .password(this.passwordEncoder().encode("max123"))
		      .roles("NORMAL");
		 
		 auth
		 .inMemoryAuthentication()
		 .withUser("harsh")
		 .password(this.passwordEncoder().encode("harsh123"))
	      .roles("ADMIN");
		 
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}

	/* 1. Basic Authentication, simple and fast
	 *    No Logout option. user/pwd requires for each request
	 *    Not recommended
	 * 
	 * (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	
	
	/* 2. Role Based authentication, Role has high overview, like what user can access
	 *    like Read, Write, update.
	 *    Authority: is a type of permission that user can do
	 *    Based on assign roles, /public can only be access by NORMAL USER
	 *    and /admin can only be access by ADMIN people
	 * 
	 * 
	 * (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		   .authorizeRequests()
		   //.antMatchers("/home", "/login", "/register").permitAll()
		   .antMatchers("/login").permitAll()
		   .antMatchers("/public/**").hasRole("NORMAL")
		   .antMatchers("/admin/**").hasRole("ADMIN")		   
		   .anyRequest()
		   .authenticated()
		   .and()
		   .formLogin();     //.httpBasic();	   (basic authentication)
		   
		
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	//  
	/*  for NoOpPasswordEncoder(no password required, but not recommended)
	 * 
	 * @SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}*/

}
