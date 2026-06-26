package com.xingjin.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 操作日志实体类
 * 用于记录用户操作行为的日志信息
 */
@Data
public class OperationLog {
    /**
     * 日志ID，主键标识
     */
    private Long id;

    /**
     * 用户ID，执行操作的用户唯一标识
     */
    private Long userId;

    /**
     * 用户名，执行操作的用户名称
     */
    private String username;

    /**
     * 操作类型，描述用户执行的具体操作
     */
    private String operation;

    /**
     * 请求方法，记录HTTP请求的方法类型(GET/POST等)
     */
    private String method;

    /**
     * 请求参数，记录操作时传递的参数信息
     */
    private String params;

    /**
     * IP地址，记录发起操作的客户端IP地址
     */
    private String ip;

    /**
     * 创建时间，记录操作发生的时间
     */
    private LocalDateTime createdTime;
}

