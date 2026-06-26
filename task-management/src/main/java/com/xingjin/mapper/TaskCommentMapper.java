package com.xingjin.mapper;

import com.xingjin.entity.TaskComment;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 任务评论数据访问接口
 * 提供任务评论的查询和插入操作
 */
@Mapper
public interface TaskCommentMapper {
    /**
     * 根据任务ID查询评论列表
     * 查询结果按创建时间倒序排列，并关联用户信息获取用户名
     *
     * @param taskId 任务ID
     * @return 评论列表，包含评论信息和对应的用户名
     */
    @Select("SELECT c.*, u.username FROM task_comment c LEFT JOIN user u ON c.user_id = u.id WHERE c.task_id = #{taskId} ORDER BY c.created_time DESC")
    List<TaskComment> findByTaskId(Long taskId);

    /**
     * 插入新的任务评论
     *
     * @param comment 任务评论对象，包含评论内容、任务ID和用户ID
     * @return 受影响的行数
     */
    @Insert("INSERT INTO task_comment(content, task_id, user_id) VALUES(#{content}, #{taskId}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TaskComment comment);
}

