package com.liu.studentmanagement.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Schema(description = "学生输入表单对象")
public class StudentDTO {

    // 新增时 ID 为空，修改时 ID 必填
    @Schema(description = "主键ID(修改时必填)")
    private Integer id;

    @NotBlank(message = "学号不能为空")
    @Schema(description = "学号")
    private String studentNo;

    @NotBlank(message = "姓名不能为空")
    @Schema(description = "姓名")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Range(min = 0, max = 120, message = "年龄不合法")
    @Schema(description = "年龄")
    private Integer age;

    @NotNull(message = "必须选择班级")
    @Schema(description = "班级ID")
    private Integer clazzId;

    @NotNull(message = "性别不能为空")
    @Range(min = 0, max = 1, message = "性别：1-男，0-女, 请重新输入")
    @Schema(description = "性别：1-男，0-女")
    private Integer gender;

    @Schema(description = "电话号码")
    @NotBlank(message = "电话号码不能为空")
    private String phoneNumber;




}