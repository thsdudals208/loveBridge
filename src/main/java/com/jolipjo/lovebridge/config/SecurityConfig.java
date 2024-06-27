package com.jolipjo.lovebridge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // TODO: 사용자 정보 객체 생성
//    private final UserDetailsService userDetailsService;

//    @Value("${security.rememberMe.tokenValiditySeconds}")
//    private int rememberMeMaxAgeSeconds;

    /*시큐리티 설정*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*url 경로 권한*/
        http
                .authorizeHttpRequests( (auth) -> auth
                        .anyRequest().permitAll()// 모든 경로 전부 허용
                );

        /*로그인 페이지*/
        http
                .formLogin( (form) -> form
                        .loginPage("/login")// 로그인 페이지 url
                        .loginProcessingUrl("/loginProc")// 로그인 처리 경로
                        .permitAll()
                );

        /*자동 로그인*/
//        http
//                .rememberMe((remember) -> remember
//                        .rememberMeParameter("remember")
//                        .tokenValiditySeconds(rememberMeMaxAgeSeconds)
//                        .alwaysRemember(false)
//                        .userDetailsService(userDetailsService)
//                );
//
//        /*로그아웃 페이지*/
//        http
//                .logout( (logout) ->
//                        logout.logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                );

        /*CSRF 끔(개발용)*/
        http
                .csrf( (csrf) -> csrf.disable());

        /*세션 설정*/
        http
                .sessionManagement((session) -> session
                        .sessionFixation().changeSessionId()// 세션 고정 공격 방어
                        .maximumSessions(3)// 동시 로그인 기기는 3대가 최대
                        .maxSessionsPreventsLogin(false)// 3대 넘게 로그인하면 하나 강제로 로그아웃 시킴
                );

        return http.build();
    }

    /*비밀번호 암호화*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
