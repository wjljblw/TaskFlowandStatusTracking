package com.xingjin.mapper;

import com.xingjin.entity.Category;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * CategoryMapper接口用于操作分类数据表
 * 提供了对category表的增删改查功能
 */
@Mapper
public interface CategoryMapper {
    /**
     * 查询所有分类信息
     * 按照id升序排列返回所有分类记录
     *
     * @return 分类对象列表
     */
    @Select("SELECT * FROM category ORDER BY id")
    List<Category> findAll();

    /**
     * 根据ID查询分类信息
     *
     * @param id 分类唯一标识符
     * @return 对应ID的分类对象，如果不存在则返回null
     */
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category findById(Long id);

    /**
     * 插入新的分类记录
     *
     * @param category 包含分类名称和描述信息的分类对象
     * @return 受影响的行数
     */
    @Insert("INSERT INTO category(name, description) VALUES(#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    /**
     * 更新分类信息
     * 根据分类ID更新分类名称和描述
     *
     * @param category 包含分类ID、名称和描述信息的分类对象
     * @return 受影响的行数
     */
    @Update("UPDATE category SET name = #{name}, description = #{description} WHERE id = #{id}")
    int update(Category category);

    /**
     * 删除指定ID的分类记录
     *
     * @param id 要删除的分类唯一标识符
     * @return 受影响的行数
     */
    @Delete("DELETE FROM category WHERE id = #{id}")
    int delete(Long id);
}

