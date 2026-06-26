package com.xingjin.interceptor;

import com.xingjin.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器类
 * 用于拦截未登录用户的请求，强制跳转到登录页面
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前进行拦截处理
     * 检查用户是否已登录，如果未登录则重定向到登录页面
     *
     * @param request  HTTP请求对象，用于获取会话信息
     * @param response HTTP响应对象，用于重定向操作
     * @param handler  处理器对象
     * @return boolean 返回true表示放行请求，返回false表示拦截请求
     * @throws Exception 处理过程中可能抛出的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从会话中获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        // 检查用户是否未登录
        if (user == null) {
            // 未登录用户重定向到登录页面
            response.sendRedirect("/login");
            return false;
        }
        // 已登录用户放行
        return true;
    }
}

