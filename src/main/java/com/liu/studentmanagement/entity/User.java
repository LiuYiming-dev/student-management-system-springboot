package com.liu.studentmanagement.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.liu.studentmanagement.common.enums.GenderEnum;
import com.liu.studentmanagement.common.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_user")
@Schema(description = "管理者实体类")
public class User {
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")// 字符串不能为 null 且 trim() 后长度 > 0
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "姓名不能为空")
    private String password;

    @Schema(description = "别名")
    private String nickname;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "权限")
    @NotNull(message = "权限不能为空")
    private RoleEnum role;


}
