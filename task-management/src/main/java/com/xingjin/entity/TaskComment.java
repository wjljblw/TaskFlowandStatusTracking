package com.xingjin.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 任务评论实体类
 * 用于表示任务系统中的评论信息，包含评论内容、关联的任务和用户等基本信息
 */
@Data
public class TaskComment {
    /** 评论唯一标识ID */
    private Long id;

    /** 评论内容 */
    private String content;

    /** 关联的任务ID */
    private Long taskId;

    /** 发表评论的用户ID */
    private Long userId;

    /** 评论创建时间 */
    private LocalDateTime createdTime;

    /** 用户名称（冗余字段，便于显示） */
    private String username;
}

