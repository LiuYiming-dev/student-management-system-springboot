package com.liu.studentmanagement.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.liu.studentmanagement.common.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data; // Lombok注解


import java.time.LocalDateTime;

@Data // 自动生成Get/Set/ToString
@TableName("student")
@Schema(description = "学生实体类")
public class Student {
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "学号")
    @NotBlank(message = "学号不能为空")
    private String studentNo; // 注意驼峰命名对应数据库下划线

    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")// 字符串不能为 null 且 trim() 后长度 > 0
    private String name;

    @Schema(description = "班级")
    @NotNull(message = "班级不能为空")
    private Integer clazzId;

    @Schema(description = "年龄")
    @Min(value = 0, message = "年龄不能小于0岁")
    @Max(value = 120, message = "年龄不能大于120岁")
    private Integer age;

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "有没有被删除")
    @NotNull(message = "删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "性别")
    @NotNull(message = "性别不能为空")
    private GenderEnum gender;

    @Schema(description = "创建管理员")
    @TableField(fill = FieldFill.INSERT)
    private Integer createByUserId;

    @Schema(description = "更新管理员")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateByUserId;
}