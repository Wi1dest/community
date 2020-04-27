package com.lsy.community.controller.interceptor;

import com.lsy.community.annotation.LoginRequired;
import com.lsy.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author : Lo Shu-ngan
 * @Classname LoginRequiredInterceptor
 * @Description 为@LoginRequired 注解写拦截器
 * @Date 2020/04/27 22:31
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 看拦截的是否是一个方法
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //尝试去取这个方法的注解上是否有@LoginRequired
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            //如果获取到该方法有@LoginRequired注解,同时又没登录信息,拒绝访问
            if (loginRequired != null && hostHolder.getUser() == null){
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}
