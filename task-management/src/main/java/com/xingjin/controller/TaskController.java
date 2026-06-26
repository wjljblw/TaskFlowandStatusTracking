package com.xingjin.controller;

import com.xingjin.common.Result;
import com.xingjin.entity.Task;
import com.xingjin.entity.TaskComment;
import com.xingjin.entity.User;
import com.xingjin.service.CategoryService;
import com.xingjin.service.TaskCommentService;
import com.xingjin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * 任务管理控制器，用于处理任务相关的请求操作。
 */
@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TaskCommentService commentService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 显示当前用户的所有任务列表页面。
     *
     * @param model   模型对象，用于向视图传递数据
     * @param session HTTP会话对象，用于获取当前登录用户信息
     * @return 返回任务列表页面路径
     */
    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("tasks", taskService.findByUserId(user.getId()));
        model.addAttribute("categories", categoryService.findAll());
        return "task/list";
    }

    /**
     * 跳转到创建任务页面。
     *
     * @param model 模型对象，用于向视图传递分类数据
     * @return 返回任务创建页面路径
     */
    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "task/create";
    }

    /**
     * 保存新创建或编辑的任务信息，并支持上传图片附件。
     *
     * @param task   任务实体对象，包含任务的基本信息
     * @param image  可选的图片文件上传参数
     * @param session HTTP会话对象，用于获取当前登录用户ID
     * @return 返回操作结果封装对象，包含成功或失败提示信息
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<String> save(Task task, @RequestParam(required = false) MultipartFile image, HttpSession session) {
        User user = (User) session.getAttribute("user");
        task.setUserId(user.getId());

        // 处理图片上传逻辑：如果提供了有效的图片文件，则将其保存至指定目录并设置访问URL
        if (image != null && !image.isEmpty()) {
            try {
                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                File dir = new File(uploadPath).getAbsoluteFile();
                if (!dir.exists()) dir.mkdirs();
                File destFile = new File(dir, filename);
                image.transferTo(destFile);
                task.setImageUrl("/uploads/" + filename);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("图片上传失败: " + e.getMessage());
            }
        }

        if (taskService.save(task)) {
            return Result.success("保存成功");
        }
        return Result.error("保存失败");
    }

    /**
     * 展示某个具体任务的详细信息及评论内容。
     *
     * @param id    任务唯一标识符
     * @param model 模型对象，用于向前端传递任务详情和相关评论数据
     * @return 返回任务详情页面路径
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("comments", commentService.findByTaskId(id));
        return "task/detail";
    }

    /**
     * 添加一条针对特定任务的评论。
     *
     * @param taskId  所属任务的ID
     * @param content 评论的内容文本
     * @param session HTTP会话对象，用于获取当前用户的ID
     * @return 返回操作结果封装对象，表示评论是否提交成功
     */
    @PostMapping("/comment")
    @ResponseBody
    public Result<String> addComment(@RequestParam Long taskId, @RequestParam String content, HttpSession session) {
        User user = (User) session.getAttribute("user");
        TaskComment comment = new TaskComment();
        comment.setTaskId(taskId);
        comment.setContent(content);
        comment.setUserId(user.getId());
        if (commentService.save(comment)) {
            return Result.success("留言成功");
        }
        return Result.error("留言失败");
    }

    /**
     * 获取并展示当前用户的任务统计信息。
     *
     * @param model   模型对象，用于将统计数据传给前端页面
     * @param session HTTP会话对象，用于获取当前登录用户的信息
     * @return 返回任务统计页面路径
     */
    @GetMapping("/stats")
    public String stats(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> stats = taskService.getUserStats(user.getId());
        model.addAttribute("stats", stats);
        return "task/stats";
    }
}
