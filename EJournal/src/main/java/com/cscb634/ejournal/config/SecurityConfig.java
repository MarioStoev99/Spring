//package com.cscb634.ejournal.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .requestMatchers("/api/v1/login/**").permitAll()
//                .requestMatchers("/api/v1/teacher/**").hasAuthority("ROLE_TEACHER")
//                .requestMatchers("/api/v1/parent/**").hasAuthority("ROLE_PARENT")
//                .requestMatchers("/api/v1/student/**").hasAuthority("ROLE_STUDENT")
//                .requestMatchers("/api/v1/**").hasAuthority("ROLE_DIRECTOR")
//                .requestMatchers("/api/v1/**").hasAuthority("ROLE_ADMINISTRATOR")
//                .and()
//                .csrf().disable();
//        return http.build();
//    }
//}