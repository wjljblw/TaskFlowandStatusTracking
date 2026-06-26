package com.xingjin.mapper;

import com.xingjin.entity.Task;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

/**
 * 任务数据访问接口，提供对任务表的各种数据库操作方法。
 */
@Mapper
public interface TaskMapper {

    /**
     * 根据用户ID查询该用户的所有任务，并关联查询用户名和分类名称。
     * 查询结果按照创建时间倒序排列。
     *
     * @param userId 用户ID
     * @return 该用户的所有任务列表
     */
    @Select("SELECT t.*, u.username, c.name as category_name FROM task t " +
            "LEFT JOIN user u ON t.user_id = u.id " +
            "LEFT JOIN category c ON t.category_id = c.id " +
            "WHERE t.user_id = #{userId} ORDER BY t.created_time DESC")
    List<Task> findByUserId(Long userId);

    /**
     * 查询所有任务信息，并关联查询用户名和分类名称。
     * 查询结果按照创建时间倒序排列。
     *
     * @return 所有任务的列表
     */
    @Select("SELECT t.*, u.username, c.name as category_name FROM task t " +
            "LEFT JOIN user u ON t.user_id = u.id " +
            "LEFT JOIN category c ON t.category_id = c.id " +
            "ORDER BY t.created_time DESC")
    List<Task> findAll();

    /**
     * 根据任务ID查询单个任务详细信息，并关联查询用户名和分类名称。
     *
     * @param id 任务ID
     * @return 对应的任务对象，如果不存在则返回null
     */
    @Select("SELECT t.*, u.username, c.name as category_name FROM task t " +
            "LEFT JOIN user u ON t.user_id = u.id " +
            "LEFT JOIN category c ON t.category_id = c.id " +
            "WHERE t.id = #{id}")
    Task findById(Long id);

    /**
     * 插入一个新的任务记录到数据库中。
     *
     * @param task 要插入的任务对象
     * @return 受影响的行数（通常为1）
     */
    @Insert("INSERT INTO task(title, description, status, image_url, user_id, category_id) " +
            "VALUES(#{title}, #{description}, #{status}, #{imageUrl}, #{userId}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Task task);

    /**
     * 更新指定任务的信息（标题、描述、状态、图片URL、分类ID）。
     *
     * @param task 包含更新信息的任务对象
     * @return 受影响的行数（通常为1）
     */
    @Update("UPDATE task SET title=#{title}, description=#{description}, status=#{status}, " +
            "image_url=#{imageUrl}, category_id=#{categoryId} WHERE id=#{id}")
    int update(Task task);

    /**
     * 更新指定任务的状态。
     *
     * @param id     任务ID
     * @param status 新的状态值
     * @return 受影响的行数（通常为1）
     */
    @Update("UPDATE task SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 删除指定ID的任务记录。
     *
     * @param id 要删除的任务ID
     * @return 受影响的行数（通常为1）
     */
    @Delete("DELETE FROM task WHERE id = #{id}")
    int delete(Long id);

    // ==================== 统计相关方法 ====================

    /**
     * 统计指定用户的任务总数。
     *
     * @param userId 用户ID
     * @return 该用户的任务数量
     */
    @Select("SELECT COUNT(*) FROM task WHERE user_id = #{userId}")
    int countByUserId(Long userId);

    /**
     * 统计指定用户且具有特定状态的任务数量。
     *
     * @param userId 用户ID
     * @param status 状态值
     * @return 符合条件的任务数量
     */
    @Select("SELECT COUNT(*) FROM task WHERE user_id = #{userId} AND status = #{status}")
    int countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 统计系统中所有任务的数量。
     *
     * @return 所有任务的总数量
     */
    @Select("SELECT COUNT(*) FROM task")
    int countAll();

    /**
     * 统计具有特定状态的任务数量。
     *
     * @param status 状态值
     * @return 具有该状态的任务数量
     */
    @Select("SELECT COUNT(*) FROM task WHERE status = #{status}")
    int countByStatus(String status);

    /**
     * 按照任务状态进行分组统计，获取每种状态的任务数量。
     *
     * @return 包含状态和对应数量的映射列表
     */
    @Select("SELECT status, COUNT(*) as count FROM task GROUP BY status")
    List<Map<String, Object>> countGroupByStatus();
}
