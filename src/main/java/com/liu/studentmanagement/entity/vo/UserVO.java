package com.liu.studentmanagement.entity.vo;

import com.liu.studentmanagement.common.enums.RoleEnum;
import lombok.Data;

@Data
public class UserVO {
    private Integer id;
    private String username;
    private String nickname;
    private RoleEnum role;
}
