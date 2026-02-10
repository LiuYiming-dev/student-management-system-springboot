package com.liu.studentmanagement.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.liu.studentmanagement.common.enums.GenderEnum;
import com.liu.studentmanagement.common.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    private Integer id;
    private String username;
    private String nickname;
    private RoleEnum role;
}
