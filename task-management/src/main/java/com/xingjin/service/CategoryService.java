package com.xingjin.service;

import com.xingjin.entity.Category;
import com.xingjin.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 分类服务类
 * 提供分类相关的业务逻辑处理
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询所有分类信息
     * @return 分类列表
     */
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    /**
     * 根据ID查询分类信息
     * @param id 分类ID
     * @return 分类对象
     */
    public Category findById(Long id) {
        return categoryMapper.findById(id);
    }

    /**
     * 保存分类信息（新增或更新）
     * @param category 分类对象
     * @return 保存是否成功
     */
    public boolean save(Category category) {
        // 根据ID判断是新增还是更新操作
        if (category.getId() == null) {
            return categoryMapper.insert(category) > 0;
        }
        return categoryMapper.update(category) > 0;
    }

    /**
     * 删除分类信息
     * @param id 分类ID
     * @return 删除是否成功
     */
    public boolean delete(Long id) {
        return categoryMapper.delete(id) > 0;
    }
}

