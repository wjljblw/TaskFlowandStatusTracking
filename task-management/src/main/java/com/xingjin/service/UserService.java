package com.xingjin.service;

import com.xingjin.entity.User;
import com.xingjin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务类，提供用户登录、注册和查询功能
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 验证成功的用户对象，验证失败返回null
     */
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        // 验证用户是否存在且密码正确
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * 用户注册
     * @param user 待注册的用户信息
     * @return 注册成功返回true，用户名已存在返回false
     */
    public boolean register(User user) {
        // 检查用户名是否已存在
        if (userMapper.countByUsername(user.getUsername()) > 0) {
            return false;
        }
        user.setRole("USER");
        return userMapper.insert(user) > 0;
    }

    /**
     * 根据ID查找用户
     * @param id 用户ID
     * @return 对应的用户对象
     */
    public User findById(Long id) {
        return userMapper.findById(id);
    }
}
