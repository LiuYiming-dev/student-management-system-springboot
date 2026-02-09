package com.liu.studentmanagement.service.userService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.studentmanagement.entity.User;
import com.liu.studentmanagement.entity.dto.UserDTO;

public interface IUserService extends IService<User> {
    void userRegister(UserDTO userDTO);
    String login(UserDTO login);
    User getByName(String name);
}
