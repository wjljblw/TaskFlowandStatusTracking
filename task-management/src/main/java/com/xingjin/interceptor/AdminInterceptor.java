package com.xingjin.interceptor;

import com.xingjin.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员拦截器类
 * 用于拦截需要管理员权限的请求，验证用户是否具有管理员角色
 */
public class AdminInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前进行拦截处理
     * 验证当前用户是否为管理员角色，如果不是则重定向到登录页面
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

        // 验证用户是否为空或不是管理员角色
        if (user == null || !"ADMIN".equals(user.getRole())) {
            // 用户未登录或不是管理员，重定向到登录页面
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}

