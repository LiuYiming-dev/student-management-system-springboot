package com.liu.studentmanagement.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDTO {
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    private String avatar; // 头像地址
}