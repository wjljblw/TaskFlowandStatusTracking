package com.xingjin.config;

import com.xingjin.interceptor.AdminInterceptor;
import com.xingjin.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，用于配置拦截器和静态资源处理器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 添加拦截器配置
     * 配置登录拦截器和管理员拦截器的拦截路径和排除路径
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置登录拦截器，拦截用户和任务相关接口，但排除登录、注册和验证码接口
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/user/**", "/task/**")
                .excludePathPatterns("/user/login", "/user/register", "/user/captcha");

        // 配置管理员拦截器，拦截所有管理员接口
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**");
    }

    /**
     * 添加静态资源处理器配置
     * 将/uploads/**路径映射到实际的文件上传目录
     * @param registry 资源处理器注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置上传文件的访问路径，将/uploads/**映射到实际的文件存储路径
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath);
    }
}

