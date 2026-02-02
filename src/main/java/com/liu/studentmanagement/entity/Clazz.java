package com.liu.studentmanagement.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("Clazz")
@Schema(description = "班级实体类")
public class Clazz {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @NotBlank(message = "班级不能为空")
    @Schema(description = "班级")
    private String className;

    @NotBlank(message = "老师不能为空")
    @Schema(description = "老师")
    private String teacherName;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
