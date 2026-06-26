package com.xingjin.controller;

import com.xingjin.common.Result;
import com.xingjin.entity.User;
import com.xingjin.service.UserService;
import com.xingjin.util.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器类，处理用户相关的请求，包括登录、注册、注销和验证码功能。
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根路径重定向到登录页面。
     *
     * @return 重定向到 /login 的字符串
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    /**
     * 显示登录页面。
     *
     * @return 登录页面视图名称
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * 显示注册页面。
     *
     * @return 注册页面视图名称
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /**
     * 获取验证码图片并存储验证码文本到会话中。
     *
     * @param session HTTP 会话对象，用于存储验证码文本
     * @return 包含验证码图片 Base64 编码的结果对象
     */
    @GetMapping("/user/captcha")
    @ResponseBody
    public Result<Map<String, String>> getCaptcha(HttpSession session) {
        String[] captcha = CaptchaUtil.generateCaptcha();
        session.setAttribute("captcha", captcha[0]);
        Map<String, String> result = new HashMap<>();
        result.put("image", captcha[1]);
        return Result.success(result);
    }

    /**
     * 处理用户登录请求，验证用户名、密码和验证码。
     *
     * @param username 用户名
     * @param password 密码
     * @param captcha  验证码
     * @param session  HTTP 会话对象，用于获取和存储用户信息
     * @return 登录结果，成功时返回用户信息，失败时返回错误信息
     */
    @PostMapping("/user/login")
    @ResponseBody
    public Result<User> login(@RequestParam String username, @RequestParam String password,
                              @RequestParam String captcha, HttpSession session) {
        // 验证验证码是否正确
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha)) {
            return Result.error("验证码错误");
        }
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return Result.success(user);
        }
        return Result.error("用户名或密码错误");
    }

    /**
     * 处理用户注册请求，验证验证码后尝试注册新用户。
     *
     * @param username 用户名
     * @param password 密码
     * @param email    邮箱地址
     * @param captcha  验证码
     * @param session  HTTP 会话对象，用于验证验证码
     * @return 注册结果，成功时返回成功消息，失败时返回错误信息
     */
    @PostMapping("/user/register")
    @ResponseBody
    public Result<String> register(@RequestParam String username, @RequestParam String password,
                                   @RequestParam String email, @RequestParam String captcha, HttpSession session) {
        // 验证验证码是否正确
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha)) {
            return Result.error("验证码错误");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        if (userService.register(user)) {
            return Result.success("注册成功");
        }
        return Result.error("用户名已存在");
    }

    /**
     * 处理用户注销请求，清除会话并重定向到登录页面。
     *
     * @param session HTTP 会话对象，将被销毁
     * @return 重定向到登录页面的字符串
     */
    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
