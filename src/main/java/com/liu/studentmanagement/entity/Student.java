package com.liu.studentmanagement.entity;

import lombok.Data; // Lombok注解

@Data // 自动生成Get/Set/ToString
public class Student {
    private Integer id;
    private String studentNo; // 注意驼峰命名对应数据库下划线
    private String name;
    private Integer age;
    private String email;
}