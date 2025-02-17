package com.czk.springboot_mybatis.service;

import com.czk.springboot_mybatis.pojo.User;

public interface UserService {
    User register(String username, String password);
    User login(String username, String password);
}
