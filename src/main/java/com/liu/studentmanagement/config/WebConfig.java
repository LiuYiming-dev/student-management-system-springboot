package com.liu.studentmanagement.config;

import com.liu.studentmanagement.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

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
                        "/swagger-resources/**",
                        "/images/**"
                );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 🌟 将 Web 虚拟路径 /images/** 映射到本地硬盘真实路径
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:D:/upload/student_management/");
    }
}