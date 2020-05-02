package com.lsy.community.controller.interceptor;

import com.lsy.community.entity.User;
import com.lsy.community.service.MessageService;
import com.lsy.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author : Lo Shu-ngan
 * @Classname MessageInterceptor
 * @Description 消息拦截器
 * @Date 2020/05/02 22:50
 */
@Component
public class MessageInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageService messageService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null){
            int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(),null);
            int noticeUnreadCount = messageService.findNoticeUnreadCount(user.getId(),null);
            modelAndView.addObject("allUnreadCount",letterUnreadCount + noticeUnreadCount);
        }
    }
}
