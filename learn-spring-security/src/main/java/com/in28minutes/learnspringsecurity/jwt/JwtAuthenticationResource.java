package com.in28minutes.learnspringsecurity.jwt;

import java.time.Instant;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class JwtAuthenticationResource {

    // JwtEncoder 주입
    private JwtEncoder jwtEncoder;
	
    // JwtEncoder를 주입 받는 생성자
    public JwtAuthenticationResource(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
	
    // POST 요청을 처리하는 메소드, /authenticate 엔드포인트
    @PostMapping("/authenticate")
    public JwtResponse authenticate(Authentication authentication) {
        return new JwtResponse(createToken(authentication));
    }

    // JWT 토큰 생성 메소드
    private String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder()
                                 .issuer("self") // 토큰 발급자 설정
                                 .issuedAt(Instant.now()) // 토큰 발급 시간 설정
                                 .expiresAt(Instant.now().plusSeconds(60 * 30)) // 토큰 만료 시간 설정 (30분)
                                 .subject(authentication.getName()) // 토큰 주제 설정 (사용자 이름)
                                 .claim("scope", createScope(authentication)) // 추가 클레임 설정
                                 .build();
		
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    // 스코프 생성 메소드
    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                               .map(a -> a.getAuthority())
                               .collect(Collectors.joining(" "));
    }
}

// JWT 토큰 응답용 레코드
record JwtResponse(String token) {}