package com.liu.studentmanagement.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.entity.User;
import com.liu.studentmanagement.entity.dto.UserDTO;
import com.liu.studentmanagement.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

        // 🌟 核心：加密后再存入数据库
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.save(user);
    }


}
