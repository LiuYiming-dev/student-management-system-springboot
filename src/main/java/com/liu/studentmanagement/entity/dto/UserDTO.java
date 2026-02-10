package com.liu.studentmanagement.entity.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "管理者输入表单对象")
public class UserDTO {

    @Schema(description = "账号")
    @NotBlank(message = "账号不能为空")// 字符串不能为 null 且 trim() 后长度 > 0
    private String username;

    @Schema(description = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "role")
    private String role;
}


