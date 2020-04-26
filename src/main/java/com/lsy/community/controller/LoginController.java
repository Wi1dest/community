package com.lsy.community.controller;

import com.lsy.community.entity.User;
import com.lsy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

import static com.lsy.community.util.CommunityConstant.ACTIVATION_REPEAT;
import static com.lsy.community.util.CommunityConstant.ACTIVATION_SUCCESS;

/**
 * @Author : Lo Shu-ngan
 * @Classname LoginController
 * @Description 登录注册控制器
 * @Date 2020/04/26 15:50
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 获取注册界面
     * @return
     */
    @GetMapping("/register")
    public String getRegisterPage(){
        return "site/register";
    }

    /**
     * 获取登录界面
     * @return
     */
    @GetMapping("/login")
    public String getLoginPage(){
        return "site/login";
    }

    /**
     * 注册
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/register")
    public String register(User user, Model model){
        Map<String,Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg","注册成功,我们已经向您的邮箱发送了一份激活邮件,请尽快激活!");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "site/register";
        }
    }

    /**
     * 激活
     * @param model
     * @param userId
     * @param code
     * @return
     */
    @GetMapping("/activation/{userId}/{code}")
    public String activation(Model model, @PathVariable("userId")Integer userId,@PathVariable("code")String code){
        int activation = userService.activation(userId, code);
        if (activation == ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功!");
            model.addAttribute("target","/login");
        }else if (activation == ACTIVATION_REPEAT){
            model.addAttribute("msg","请勿重复激活!");
            model.addAttribute("target","/index");
        }else {
            model.addAttribute("msg","激活失败!您提供的激活码有误!");
            model.addAttribute("target","/index");
        }
        return "/site/operate-result";
    }


}
