package com.czk.springboot_mybatis.service.impl;

import com.czk.springboot_mybatis.mapper.UserMapper;
import com.czk.springboot_mybatis.pojo.User;
import com.czk.springboot_mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(String username, String password){
        User existingUser = userMapper.selectUserByUsername(username);
        if (existingUser != null) {
            return null;
        }
        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setCreateTime(LocalDateTime.now());
        user.setStatus(1);
        int userid = userMapper.insertUser(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            return null;
        }
        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!hashedPassword.equals(user.getPassword())) {
            return null;
        }
        return user;
    }
}
