package com.lsy.community.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname ServiceAspect
 * @Description AOP
 * @Date 2020/04/30 18:04
 */
@Component
@Aspect
@Slf4j
public class ServiceAspect {
    //@Component
    //@Aspect
    //@Slf4j
    @Pointcut("execution(* com.lsy.community.service.*.*(..))")
    public void pointcut(){
    }

    @Before("pointcut()")
    public void befote(JoinPoint joinPoint){
        // 用户[1.2.3.4] 在[xxx], 访问了 [com.lsy.community.service.xxx()]
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        log.info(String.format("用户[%s],在[%s],访问了[%s].",ip,now,target));
    }
}
