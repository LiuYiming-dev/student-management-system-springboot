package com.liu.studentmanagement.controller;

import com.liu.studentmanagement.entity.User;
import com.liu.studentmanagement.entity.dto.PasswordUpdateDTO;
import com.liu.studentmanagement.entity.vo.LoginVO;
import com.liu.studentmanagement.entity.vo.UserVO;
import com.liu.studentmanagement.service.userService.UserServiceImpl;
import com.liu.studentmanagement.common.Result;
import com.liu.studentmanagement.entity.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController // 表示返回的是数据不是页面
@RequestMapping(("/user")) // 统一前缀
@CrossOrigin // 🌟重要！允许跨域，为了以后Vue能访问
@Tag(name = "管理员管理模块", description = "负责管理员的登录") // 🌟 描述这个 Controller
public class UserController {
    final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "管理员注册")
    public Result<?> register(@RequestBody @Validated UserDTO userDTO) {
        userService.userRegister(userDTO);
        return Result.success(null);
    }

    @PostMapping("/login")
    @Operation(summary = "管理员登录")
    public Result<LoginVO> login(@RequestBody @Validated UserDTO userDTO) {
        String token = userService.login(userDTO);
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);

        User user = userService.getByName(userDTO.getUsername());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        loginVO.setUser(userVO);
        return Result.success(loginVO);
    }

    @PutMapping("/password")
    @Operation(summary = "修改当前登录用户密码")
    public Result<?> updatePassword(@RequestBody @Validated PasswordUpdateDTO passwordUpdateDTO) {
        userService.updatePassword(passwordUpdateDTO);
        return Result.success(null);
    }
}
