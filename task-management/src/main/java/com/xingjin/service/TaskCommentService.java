package com.xingjin.service;

import com.xingjin.entity.TaskComment;
import com.xingjin.mapper.TaskCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 任务评论服务类
 * 提供任务评论的查询和保存功能
 */
@Service
public class TaskCommentService {
    @Autowired
    private TaskCommentMapper commentMapper;

    /**
     * 根据任务ID查询评论列表
     * @param taskId 任务ID
     * @return 任务评论列表
     */
    public List<TaskComment> findByTaskId(Long taskId) {
        return commentMapper.findByTaskId(taskId);
    }

    /**
     * 保存任务评论
     * @param comment 任务评论对象
     * @return 保存成功返回true，否则返回false
     */
    public boolean save(TaskComment comment) {
        return commentMapper.insert(comment) > 0;
    }
}

