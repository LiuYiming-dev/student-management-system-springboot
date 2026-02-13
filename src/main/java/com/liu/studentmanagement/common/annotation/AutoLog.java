package com.liu.studentmanagement.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD) // 贴在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行期间有效
@Documented
public @interface AutoLog {
    String value() default ""; // 模块描述，如 "学生管理"
    String action() default ""; // 动作描述，如 "删除学生"
}