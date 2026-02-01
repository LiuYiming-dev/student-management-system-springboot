package com.liu.studentmanagement.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data; // Lombok注解

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data // 自动生成Get/Set/ToString
@TableName("student")
public class Student {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String studentNo; // 注意驼峰命名对应数据库下划线
    private String name;
    private Integer age;
    private String email;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}