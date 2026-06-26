package com.xingjin.service;

import com.xingjin.entity.Task;
import com.xingjin.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务服务类，提供对任务的增删改查、搜索及统计功能。
 */
@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    /**
     * 根据用户ID查找所有任务
     * @param userId 用户ID
     * @return 该用户的所有任务列表
     */
    public List<Task> findByUserId(Long userId) {
        return taskMapper.findByUserId(userId);
    }

    /**
     * 查找所有任务
     * @return 所有任务列表
     */
    public List<Task> findAll() {
        return taskMapper.findAll();
    }

    /**
     * 根据任务ID查找任务详情
     * @param id 任务ID
     * @return 对应的任务对象，若不存在则返回null
     */
    public Task findById(Long id) {
        return taskMapper.findById(id);
    }

    /**
     * 保存或更新任务信息。如果任务ID为空，则执行插入操作；否则执行更新操作。
     * 若状态为空，默认设置为"PENDING"
     * @param task 要保存的任务对象
     * @return 操作是否成功（影响记录数大于0）
     */
    public boolean save(Task task) {
        if (task.getStatus() == null) {
            task.setStatus("PENDING");
        }
        if (task.getId() == null) {
            return taskMapper.insert(task) > 0;
        }
        return taskMapper.update(task) > 0;
    }

    /**
     * 更新指定任务的状态
     * @param id 任务ID
     * @param status 新状态值
     * @return 操作是否成功（影响记录数大于0）
     */
    public boolean updateStatus(Long id, String status) {
        return taskMapper.updateStatus(id, status) > 0;
    }

    /**
     * 删除指定任务
     * @param id 任务ID
     * @return 操作是否成功（影响记录数大于0）
     */
    public boolean delete(Long id) {
        return taskMapper.delete(id) > 0;
    }

    /**
     * 多条件搜索任务：标题/描述中包含关键字、状态匹配、分类ID匹配
     * @param keyword 关键字（用于模糊匹配标题和描述）
     * @param status 任务状态筛选条件
     * @param categoryId 分类ID筛选条件
     * @return 符合条件的任务列表
     */
    public List<Task> search(String keyword, String status, Long categoryId) {
        List<Task> tasks = taskMapper.findAll();
        return tasks.stream()
            .filter(t -> keyword == null || keyword.isEmpty() ||
                    t.getTitle().contains(keyword) ||
                    (t.getDescription() != null && t.getDescription().contains(keyword)))
            .filter(t -> status == null || status.isEmpty() || t.getStatus().equals(status))
            .filter(t -> categoryId == null || 
                    (categoryId == -1 && t.getCategoryId() == null) || 
                    t.getCategoryId() != null && t.getCategoryId().equals(categoryId))
            .collect(Collectors.toList());
    }

    /**
     * 获取指定用户的任务统计数据
     * 包括总任务数、待处理、进行中、已完成数量
     * @param userId 用户ID
     * @return 统计结果Map，key包括"total","pending","inProgress","completed"
     */
    public Map<String, Object> getUserStats(Long userId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", taskMapper.countByUserId(userId));
        stats.put("pending", taskMapper.countByUserIdAndStatus(userId, "PENDING"));
        stats.put("inProgress", taskMapper.countByUserIdAndStatus(userId, "IN_PROGRESS"));
        stats.put("completed", taskMapper.countByUserIdAndStatus(userId, "COMPLETED"));
        return stats;
    }

    /**
     * 获取全局任务统计数据
     * 包括总任务数、各状态任务数以及完成率
     * @return 统计结果Map，key包括"total","pending","inProgress","completed","completionRate"
     */
    public Map<String, Object> getGlobalStats() {
        Map<String, Object> stats = new HashMap<>();
        int total = taskMapper.countAll();
        int completed = taskMapper.countByStatus("COMPLETED");
        stats.put("total", total);
        stats.put("pending", taskMapper.countByStatus("PENDING"));
        stats.put("inProgress", taskMapper.countByStatus("IN_PROGRESS"));
        stats.put("completed", completed);
        stats.put("completionRate", total > 0 ? (completed * 100 / total) : 0);
        return stats;
    }
}
