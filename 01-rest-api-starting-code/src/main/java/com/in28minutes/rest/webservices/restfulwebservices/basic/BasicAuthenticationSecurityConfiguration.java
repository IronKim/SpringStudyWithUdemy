package com.in28minutes.rest.webservices.restfulwebservices.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class BasicAuthenticationSecurityConfiguration {
	
	//Filter chain
	// authenticate all request
	//basic authentication
	//disabling csrf
	//stateless rest api
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//1: Response to preflight request doesn't pass access control check
		//2: basic auth
		return http.authorizeHttpRequests(    // 모든 HTTP 요청에 대한 권한 설정
					auth -> 
					auth
					.antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // HTTP OPTIONS 메소드에 대한 접근을 모든 사용자에게 허용
					.anyRequest().authenticated()
					)
					.httpBasic(Customizer.withDefaults())  // HTTP 기본 인증 활성화
					.sessionManagement( // 세션 관리 설정: STATELESS 모드로 세션을 생성하지 않음
							session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.csrf().disable() // CSRF 보호 비활성화
					.build();   // 구성된 SecurityFilterChain 반환
	}
}
