package com.czk.springboot_mybatis.controller;

import com.czk.springboot_mybatis.common.JsonResult;
import com.czk.springboot_mybatis.pojo.User;
import com.czk.springboot_mybatis.service.UserService;
import com.czk.springboot_mybatis.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public JsonResult<User> register(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        if (username == null || password == null) {
            return JsonResult.error(400, "用户名或密码不能为空");
        }

        User registeredUser = userService.register(username, password);
        if (registeredUser != null) {
            return JsonResult.success("注册成功", registeredUser);
        } else {
            return JsonResult.error(409, "用户名已存在");
        }
    }
    @PostMapping("/login")
    public JsonResult<Map<String, Object>> login(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        if (username == null || password == null) {
            return JsonResult.error(400, "用户名或密码不能为空");
        }

        User loggedInUser = userService.login(username, password);
        if (loggedInUser != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", loggedInUser.getId());
            claims.put("username", loggedInUser.getUsername());
            String token = jwtUtil.generateToken(claims);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", loggedInUser);
            responseData.put("token", token);
            return JsonResult.success("登录成功", responseData);
        }
        else return JsonResult.error(401, "用户名或密码错误");
    }
}
