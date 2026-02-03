package com.liu.studentmanagement.config;

import com.liu.studentmanagement.config.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 默认拦截所有路径
                .excludePathPatterns(   // 径放行以下路
                        "/user/login",    // 登录接口不能拦
                        "/user/register", // 注册接口不能拦
                        "/doc.html",      // 文档页面不能拦
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**"
                );
    }
}