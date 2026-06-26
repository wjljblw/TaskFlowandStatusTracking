package com.xingjin.mapper;

import com.xingjin.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * 用户数据访问接口
 * 提供用户相关的数据库操作方法
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户对象，如果未找到则返回null
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    /**
     * 根据用户ID查询用户信息
     * @param id 用户ID
     * @return 用户对象，如果未找到则返回null
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);

    /**
     * 插入新用户记录
     * @param user 用户对象，包含用户名、密码、邮箱和角色信息
     * @return 受影响的行数
     */
    @Insert("INSERT INTO user(username, password, email, role) VALUES(#{username}, #{password}, #{email}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /**
     * 统计指定用户名的用户数量
     * @param username 用户名
     * @return 用户数量
     */
    @Select("SELECT COUNT(*) FROM user WHERE username = #{username}")
    int countByUsername(String username);
}

