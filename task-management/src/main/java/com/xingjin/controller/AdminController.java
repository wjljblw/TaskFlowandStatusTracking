package com.xingjin.controller;

import com.xingjin.common.Result;
import com.xingjin.entity.Category;
import com.xingjin.entity.Task;
import com.xingjin.service.CategoryService;
import com.xingjin.service.OperationLogService;
import com.xingjin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 后台管理控制器，用于处理管理员相关的页面请求与业务操作。
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OperationLogService logService;

    /**
     * 显示后台仪表盘页面，加载全局任务统计信息和最近的操作日志。
     *
     * @param model 页面模型对象，用于传递数据到视图层
     * @return 返回仪表盘页面路径
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("stats", taskService.getGlobalStats());
        model.addAttribute("logs", logService.findRecent(20));
        return "admin/dashboard";
    }

    /**
     * 获取并展示任务列表，支持关键词、状态和分类筛选功能。
     *
     * @param keyword     搜索关键词（可选）
     * @param status      任务状态（可选）
     * @param categoryId  分类ID（可选）
     * @param model       页面模型对象，用于传递数据到视图层
     * @return 返回任务列表页面路径
     */
    @GetMapping("/tasks")
    public String taskList(@RequestParam(required = false) String keyword,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Long categoryId,
                          Model model) {
        List<Task> tasks = taskService.search(keyword, status, categoryId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("categoryId", categoryId);
        return "admin/tasks";
    }

    /**
     * 更新指定任务的状态。
     *
     * @param id      任务ID
     * @param status  新的任务状态
     * @return 返回操作结果封装对象，包含成功或失败提示信息
     */
    @PostMapping("/task/status")
    @ResponseBody
    public Result<String> updateStatus(@RequestParam Long id, @RequestParam String status) {
        if (taskService.updateStatus(id, status)) {
            return Result.success("状态更新成功");
        }
        return Result.error("更新失败");
    }

    /**
     * 展示所有任务分类列表。
     *
     * @param model 页面模型对象，用于传递数据到视图层
     * @return 返回分类列表页面路径
     */
    @GetMapping("/categories")
    public String categoryList(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }

    /**
     * 保存一个新的任务分类或更新已有分类信息。
     *
     * @param category 待保存的分类实体对象
     * @return 返回操作结果封装对象，包含成功或失败提示信息
     */
    @PostMapping("/category/save")
    @ResponseBody
    public Result<String> saveCategory(@RequestBody Category category) {
        if (categoryService.save(category)) {
            return Result.success("保存成功");
        }
        return Result.error("保存失败");
    }

    /**
     * 删除指定ID的任务分类。
     *
     * @param id 要删除的分类ID
     * @return 返回操作结果封装对象，包含成功或失败提示信息
     */
    @DeleteMapping("/category/{id}")
    @ResponseBody
    public Result<String> deleteCategory(@PathVariable Long id) {
        if (categoryService.delete(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 展示系统全局统计数据。
     *
     * @param model 页面模型对象，用于传递数据到视图层
     * @return 返回统计信息页面路径
     */
    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("stats", taskService.getGlobalStats());
        return "admin/stats";
    }
}
