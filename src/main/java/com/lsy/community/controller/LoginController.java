package com.lsy.community.controller;

import com.google.code.kaptcha.Producer;
import com.lsy.community.entity.User;
import com.lsy.community.service.UserService;
import com.lsy.community.util.CommunityUtil;
import com.lsy.community.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.lsy.community.util.CommunityConstant.*;

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

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private RedisTemplate redisTemplate;

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
     */
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response/*, HttpSession session*/){
        //生产验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //将验证码存入session
        //session.setAttribute("kaptcha",text);

        //验证码的归属
        String kaptchaOwner = CommunityUtil.generateUUID();
        Cookie cookie = new Cookie("kaptchaOwner",kaptchaOwner);
        cookie.setMaxAge(60);
        cookie.setPath(contextPath);
        response.addCookie(cookie);
        //将验证码存入redis
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey,text,60, TimeUnit.SECONDS);

        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            log.error("相应验证码失败: " + e.getMessage());
        }
    }

    /**
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param rememberme 是否记住我
     * @param model model模型
     * //@param session 我们在上面把验证码放在了session中,所以需要从session中取出验证码
     * @param response 我们需要把ticket放入cookie 传给客户端
     * @return
     */
    @PostMapping("/login")
    public String login(String username,String password,String code,boolean rememberme,
                        Model model,/*HttpSession session,*/HttpServletResponse response,@CookieValue("kaptchaOwner")String kaptchaOwner){
        //检查验证码
        //String kaptcha = (String) session.getAttribute("kaptcha");
        String kaptcha = null;
        if (StringUtils.isNotBlank(kaptchaOwner)){
            String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        }

        // equalsIgnoreCase可以忽略验证码大小写
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)){
            model.addAttribute("codeMsg","验证码不正确!");
            return "/site/login";
        }

        //检查账号密码
        int expiredSeconds = rememberme ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String,Object> map = userService.login(username,password,expiredSeconds);
        /*containsKey => map包好ticket*/
        if (map.containsKey("ticket")){
             /*cookie的名字和值*/
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
             /*cookie生效范围*/
            cookie.setPath(contextPath);
             /*cookie最大时长*/
            cookie.setMaxAge(expiredSeconds);
             /*放入response*/
            response.addCookie(cookie);
            return "redirect:/index";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "/site/login";
        }
    }

    @GetMapping("/logout")
    public String logout(@CookieValue("ticket")String ticket){
        userService.logout(ticket);
        return "redirect:/login";
    }

}
