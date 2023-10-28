package com.in28minutes.learnspringsecurity.basic;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableMethodSecurity 어노테이션은 메소드 레벨의 보안을 활성화
//jsr250Enabled = true: JSR-250 기반의 보안 주석을 활성화. JSR-250은 Java에서 보안을 지원하기 위한 표준 명세.
//securedEnabled = true: Spring Security에서 제공하는 @Secured 어노테이션을 활성화. @Secured 어노테이션은 메소드에 대한 보안을 정의하는 데 사용.
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class BasicAuthSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 모든 요청에 대해 인증이 필요하도록 설정
        http.authorizeHttpRequests(
        		requests -> {
        			requests
        			.requestMatchers("/users").hasRole("USER")
        			.requestMatchers("admin/**").hasRole("ADMIN")
        			.anyRequest().authenticated();
        		});
        // 세션을 상태 없는(Stateless) 모드로 설정
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        // 폼 로그인을 활성화
        //http.formLogin(withDefaults());
        
        // HTTP 기본 인증 설정
        http.httpBasic(withDefaults());
        
        // CSRF 보호 기능 비활성화
        http.csrf().disable();
        
        // X-Frame-Options 설정을 SAMEORIGIN으로 지정
        http.headers().frameOptions().sameOrigin();
        
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailService() {
//        // 내장 메모리를 사용하여 사용자를 생성하고 관리하는 UserDetailsService 빈을 생성
//        
//        var user = User.withUsername("in28minutes")
//            .password("{noop}dummy") // 비밀번호를 암호화하지 않음 (테스트용)
//            .roles("USER")
//            .build();
//        
//        var admin = User.withUsername("admin")
//                .password("{noop}dummy") // 비밀번호를 암호화하지 않음 (테스트용)
//                .roles("ADMIN")
//                .build();
//        
//        return new InMemoryUserDetailsManager(user, admin);
//    }
    
    @Bean
    public DataSource dataSource() {
        // 내장 데이터베이스 (H2)를 사용하여 DataSource 빈을 생성
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }
    
    @Bean
    public UserDetailsService userDetailService(DataSource dataSource) {
        // 데이터베이스를 사용하여 사용자를 생성하고 관리하는 UserDetailsService 빈을 생성
        
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
        
        // JDBC를 사용하여 UserDetailsManager를 생성하고 사용자를 추가
        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);
        
        return jdbcUserDetailsManager;
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder 빈을 생성하여 비밀번호를 해시화하는 데 사용
        return new BCryptPasswordEncoder();
    }
}
