package com.liu.studentmanagement.service.userService;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.common.BaseContext;
import com.liu.studentmanagement.common.enums.RoleEnum;
import com.liu.studentmanagement.entity.User;
import com.liu.studentmanagement.entity.dto.PasswordUpdateDTO;
import com.liu.studentmanagement.entity.dto.UserDTO;
import com.liu.studentmanagement.mapper.UserMapper;
import com.liu.studentmanagement.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void userRegister(UserDTO userDTO) {
//        if (this.getById(userDTO.getId()) == null) {
//            throw new RuntimeException("不存在此管理员");
//        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        RoleEnum roleEnum = RoleEnum.getByCode(userDTO.getRole());
        user.setRole(roleEnum);
        // 🌟 核心：加密后再存入数据库
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.save(user);
    }

    @Override
    public String login(UserDTO userDTO) {
        // 1. 根据用户名查数据库
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userDTO.getUsername());
        User user = this.getOne(wrapper);

        // 2. 判断用户是否存在
        if (user == null) {
            throw new RuntimeException("用户名或密码错误"); // 不要提示用户不存在，防暴力破解
        }

        // 3. 校验密码
        // 注意：第一个参数是前端传的明文，第二个是数据库里的密文
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 4. 生成并返回 Token
        return JwtUtils.createToken(user.getId(), user.getUsername(), user.getRole());
    }

    @Override
    public User getByName(String name) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, name);
        return this.getOne(wrapper);
    }

    @Override
    public void updatePassword(PasswordUpdateDTO passwordUpdateDTO) {
        Integer currentUserId = BaseContext.getCurrentId();
        User user = this.getById(currentUserId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("原密码不正确");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
        this.updateById(user);
        log.info("用户 {} 修改密码成功", user.getUsername());
    }


}
