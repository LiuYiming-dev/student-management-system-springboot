package com.liu.studentmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    // 1. 定义加密器：之后在 Service 里 @Autowired 就能用
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. 配置放行规则（Spring Boot 3 的写法）
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // 禁用 CSRF 保护（为了方便 Postman 测试）
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 暂时允许所有请求，不用登录就能访问
                );
        return http.build();
    }
}