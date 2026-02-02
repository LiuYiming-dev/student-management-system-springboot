package com.liu.studentmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi; // 🌟 注意导包
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // 1. 配置文档的基本信息（标题、作者等）
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("学生管理系统接口文档")
                        .description("基于 Spring Boot 3 + Knife4j")
                        .version("v1.0.0"));
    }

    // 2. 🌟 核心修复：配置分组，告诉它去扫描哪个包
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("student-management") // 分组名称，随便起
                .pathsToMatch("/**")         // 匹配所有路径
                .packagesToScan("com.liu.studentmanagement.controller") // 🌟 这里填你 Controller 所在的包名！！
                .build();
    }
}