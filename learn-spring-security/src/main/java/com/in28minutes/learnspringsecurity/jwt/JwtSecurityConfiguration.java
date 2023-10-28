package com.in28minutes.learnspringsecurity.jwt;

import static org.springframework.security.config.Customizer.withDefaults;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

//@Configuration
public class JwtSecurityConfiguration {
	
	// SecurityFilterChain 빈 설정
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// 모든 요청에 대해 인증이 필요하도록 설정
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		
		// 세션을 상태 없는(Stateless) 모드로 설정
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// HTTP 기본 인증 설정
		http.httpBasic(withDefaults());
		
		// CSRF 보호 기능 비활성화
		http.csrf().disable();
		
		// X-Frame-Options 설정을 SAMEORIGIN으로 지정
		http.headers().frameOptions().sameOrigin();
		
		// OAuth2 리소스 서버를 설정하여 JWT 검증 활성화
		http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		
		return http.build();
	}
	
	// 내장 데이터베이스 (H2)를 사용하여 DataSource 빈을 생성
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	// 데이터베이스를 사용하여 사용자를 생성하고 관리하는 UserDetailsService 빈을 생성
	@Bean
	public UserDetailsService userDetailService(DataSource dataSource) {
		
		// 기본 사용자와 역할을 설정
		var user = User.withUsername("in28minutes")
			.password("dummy") // 비밀번호를 인코딩하여 설정
			.passwordEncoder(str -> passwordEncoder().encode(str)) // 비밀번호 인코딩 방식 지정
			.roles("USER")
			.build();
		
		var admin = User.withUsername("admin")
				.password("dummy") // 비밀번호를 인코딩하여 설정
				.passwordEncoder(str -> passwordEncoder().encode(str)) // 비밀번호 인코딩 방식 지정
				.roles("ADMIN", "USER")
				.build();
		
		var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		
		return jdbcUserDetailsManager;
	}
	
	// BCryptPasswordEncoder 빈을 생성하여 비밀번호를 해시화하는 데 사용
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// RSA 키 생성을 위한 빈 설정
	@Bean
	public KeyPair keyPair() {
		try {
			var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			return keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	// RSA 키를 생성하여 설정하는 빈 설정
	@Bean
	public RSAKey rsaKey(KeyPair keyPair) {
		return new RSAKey
				.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey(keyPair.getPrivate())
				.keyID(UUID.randomUUID().toString())
				.build();		
	}
	
	// JWKSource 빈 설정
	@Bean
	public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
		var jwkSet = new JWKSet(rsaKey);
		
		// JWKSource를 설정하여 SecurityContext에서 JWKSet을 선택
		return (jwkSelector, context) -> jwkSelector.select(jwkSet); 
	}
	
	// JwtDecoder 빈 설정
	@Bean
	public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
		// NimbusJwtDecoder를 설정하여 RSA 공개키를 사용하여 JwtDecoder를 생성
		return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
	}
	
	// JwtEncoder 빈 설정
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		// NimbusJwtEncoder를 설정하여 JwtEncoder를 생성
		return new NimbusJwtEncoder(jwkSource);
	}
}
