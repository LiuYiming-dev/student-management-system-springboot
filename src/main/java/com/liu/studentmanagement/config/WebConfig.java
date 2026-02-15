package com.liu.studentmanagement.config;

import com.liu.studentmanagement.config.interceptor.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final String uploadPath;

    public WebConfig(JwtInterceptor jwtInterceptor, @Value("${file.upload-path}") String uploadPath) {
        this.jwtInterceptor = jwtInterceptor;
        this.uploadPath = uploadPath;
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
                .addResourceLocations("file:" + uploadPath);
        log.info("当前图片映射的物理路径为: file:{}", uploadPath);
    }
}