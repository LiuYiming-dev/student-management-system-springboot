package com.liu.studentmanagement.entity.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClazzDTO {

    @Schema(description = "主键ID")
    private Integer id;

    @NotBlank(message = "班级不能为空")
    @Schema(description = "班级")
    private String className;

    @NotBlank(message = "老师不能为空")
    @Schema(description = "老师")
    private String teacherName;

    @NotBlank(message = "电话号码不能为空")
    @Schema(description = "电话号码")
    private String phoneNumber;

}
