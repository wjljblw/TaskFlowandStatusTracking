package com.xingjin.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 用于表示系统中的用户信息，包含用户的基本属性和时间戳信息
 */
@Data
public class User {
    /**
     * 用户唯一标识符
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱地址
     */
    private String email;

    /**
     * 用户角色权限
     */
    private String role;

    /**
     * 用户创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 用户信息最后更新时间
     */
    private LocalDateTime updatedTime;
}

