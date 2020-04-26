package com.lsy.community.controller;

import com.google.code.kaptcha.Producer;
import com.lsy.community.entity.User;
import com.lsy.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
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
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

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

    /**
     * 获取验证码
     * @param response
     * @param session
     */
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        //生产验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //将验证码存入session
        session.setAttribute("kaptcha",text);

        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            log.error("相应验证码失败: " + e.getMessage());
        }
    }

}
