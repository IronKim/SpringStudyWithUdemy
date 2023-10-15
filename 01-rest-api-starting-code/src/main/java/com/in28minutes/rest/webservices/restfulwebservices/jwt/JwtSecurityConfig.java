package com.in28minutes.rest.webservices.restfulwebservices.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtSecurityConfig {

    // SecurityFilterChain을 설정하는 메소드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        return httpSecurity
                // HTTP 요청에 대한 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/authenticate").permitAll() // "/authenticate" 경로는 모든 사용자에게 허용
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // h2-console은 서블릿이며 프로덕션에서 사용하지 않는 것이 좋습니다.
                        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll() // 모든 경로에 대한 OPTIONS 요청은 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증된 사용자만 허용
                )
                // CSRF 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // 세션 관리 설정: STATELESS 모드로 세션을 생성하지 않음
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // OAuth2 리소스 서버 설정: JWT를 사용한다고 명시
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                // HTTP 기본 인증 설정
                .httpBasic(Customizer.withDefaults())
                // 응답 헤더 설정: 프레임 동일 출처 정책을 적용
                .headers(header -> {header.frameOptions().sameOrigin();})
                .build();
    }

    // AuthenticationManager를 설정하는 메소드
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authenticationProvider);
    }

    // UserDetailsService를 설정하는 메소드
    @Bean
    public UserDetailsService userDetailsService() {
        // 기본 사용자 정보를 생성하여 반환
        UserDetails user = User.withUsername("in28minutes")
                                .password("{noop}dummy")
                                .authorities("read")
                                .roles("USER")
                                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // JWKSource를 설정하는 메소드
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet jwkSet = new JWKSet(rsaKey());
        return (((jwkSelector, securityContext) -> jwkSelector.select(jwkSet)));
    }

    // JwtEncoder를 설정하는 메소드
    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    // JwtDecoder를 설정하는 메소드
    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(rsaKey().toRSAPublicKey())
                .build();
    }
    
    // RSAKey를 생성하고 반환하는 메소드
    @Bean
    public RSAKey rsaKey() {
        KeyPair keyPair = keyPair();
        return new RSAKey
                .Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    // RSA KeyPair를 생성하고 반환하는 메소드
    @Bean
    public KeyPair keyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to generate an RSA Key Pair", e);
        }
    }
}
