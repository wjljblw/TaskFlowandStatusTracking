package com.xingjin.aspect;

import com.xingjin.entity.OperationLog;
import com.xingjin.entity.User;
import com.xingjin.service.OperationLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 操作日志切面类
 * 用于拦截AdminController中的所有方法执行，在方法执行完成后记录操作日志
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    private OperationLogService logService;

    /**
     * 定义切入点，拦截AdminController类中的所有方法
     */
    @Pointcut("execution(* com.xingjin.controller.AdminController.*(..))")
    public void adminPointcut() {}

    /**
     * 后置通知方法，在目标方法执行完成后执行
     * 用于记录用户的操作日志信息
     *
     * @param joinPoint 连接点对象，包含被拦截方法的信息
     */
    @AfterReturning("adminPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        try {
            // 获取当前请求的Servlet请求属性
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs == null) return;

            HttpServletRequest request = attrs.getRequest();
            // 从session中获取当前用户信息
            User user = (User) request.getSession().getAttribute("user");

            // 构建操作日志对象
            OperationLog log = new OperationLog();
            if (user != null) {
                log.setUserId(user.getId());
                log.setUsername(user.getUsername());
            }
            // 设置操作名称和完整方法名
            log.setOperation(joinPoint.getSignature().getName());
            log.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            // 设置方法参数
            log.setParams(Arrays.toString(joinPoint.getArgs()));
            // 设置客户端IP地址
            log.setIp(request.getRemoteAddr());

            // 保存操作日志
            logService.save(log);
        } catch (Exception e) {
            // 日志记录失败不影响业务
        }
    }
}

