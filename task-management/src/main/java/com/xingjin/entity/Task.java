package com.xingjin.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 任务实体类
 * 用于表示系统中的任务信息，包含任务的基本属性以及关联的用户和分类信息
 */
@Data
public class Task {
    /**
     * 任务唯一标识符
     */
    private Long id;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务描述信息
     */
    private String description;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 任务相关的图片URL地址
     */
    private String imageUrl;

    /**
     * 关联的用户ID
     */
    private Long userId;

    /**
     * 关联的分类ID
     */
    private Long categoryId;

    /**
     * 任务创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 任务最后更新时间
     */
    private LocalDateTime updatedTime;

    // 关联字段
    /**
     * 用户名（关联字段）
     */
    private String username;

    /**
     * 分类名称（关联字段）
     */
    private String categoryName;
}

