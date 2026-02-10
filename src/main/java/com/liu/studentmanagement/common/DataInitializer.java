package com.liu.studentmanagement.common;

import com.liu.studentmanagement.common.enums.RoleEnum;
import com.liu.studentmanagement.entity.dto.UserDTO;
import com.liu.studentmanagement.service.userService.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
// CommandLineRunner 的作用：项目启动完成后，会自动执行这里的 run 方法
public class DataInitializer implements CommandLineRunner {

    private final IUserService userService;

    public DataInitializer(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // 1. 检查数据库里是否已经有用户
        long count = userService.count();

        if (count == 0) {
            log.info("检测到数据库用户表为空，正在初始化默认管理员...");
            UserDTO admin = new UserDTO();
            admin.setUsername("admin");
            admin.setPassword("123456"); // 🌟 注意：如果你的 register 方法里有加密逻辑，直接调 register
            admin.setNickname("系统管理员");
            admin.setRole(RoleEnum.ADMIN.getCode());
            userService.userRegister(admin);

            UserDTO student = new UserDTO();
            student.setUsername("student");
            student.setPassword("123456"); // 🌟 注意：如果你的 register 方法里有加密逻辑，直接调 register
            student.setNickname("学生");
            student.setRole(RoleEnum.STUDENT.getCode());
            userService.userRegister(student);
            log.info("默认管理员初始化成功！账号：student，密码：123456");
        }
    }
}