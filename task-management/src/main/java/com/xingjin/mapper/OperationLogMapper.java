package com.xingjin.mapper;

import com.xingjin.entity.OperationLog;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 操作日志数据访问接口
 * 定义了操作日志的持久化操作方法
 */
@Mapper
public interface OperationLogMapper {
    /**
     * 插入操作日志记录
     * 将操作日志信息保存到数据库中
     *
     * @param log 操作日志对象，包含用户ID、用户名、操作描述、方法名、参数和IP地址等信息
     * @return 返回插入记录的影响行数，成功插入返回1，失败返回0
     */
    @Insert("INSERT INTO operation_log(user_id, username, operation, method, params, ip) VALUES(#{userId}, #{username}, #{operation}, #{method}, #{params}, #{ip})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OperationLog log);

    /**
     * 查询最近的操作日志记录
     * 按创建时间倒序排列，获取指定数量的最新日志记录
     *
     * @param limit 要查询的记录数量上限
     * @return 返回最近的操作日志记录列表，按创建时间倒序排列
     */
    @Select("SELECT * FROM operation_log ORDER BY created_time DESC LIMIT #{limit}")
    List<OperationLog> findRecent(int limit);
}

