package com.in28minutes.rest.webservices.restfulwebservices.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 1. 모든 요청이 인증되도록 한다
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()
				);
		// 2. 요청이 인증을 받지 못하면, 웹 페이지가 나타난다.
		http.httpBasic(withDefaults());
		
		// 3. CSRF -> POST, PUT 를 해제
		http.csrf().disable();
		
		return http.build();
	}
}
