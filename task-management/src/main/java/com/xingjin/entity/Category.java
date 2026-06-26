package com.xingjin.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 分类实体类
 * 用于表示系统中的分类信息，包含分类的基本属性
 */
@Data
public class Category {
    /**
     * 分类唯一标识符
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述信息
     */
    private String description;

    /**
     * 分类创建时间
     */
    private LocalDateTime createdTime;
}

