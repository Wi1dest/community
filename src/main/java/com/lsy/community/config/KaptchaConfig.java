package com.lsy.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author : Lo Shu-ngan
 * @Classname KaptchaConfig
 * @Description 验证Kaptcha配置类
 * @Date 2020/04/26 19:55
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer(){
        Properties properties = new Properties();
        /*宽度*/
        properties.setProperty("kaptcha.image.width","100");
        /*高度*/
        properties.setProperty("kaptcha.image.height","40");
        /*字体大小*/
        properties.setProperty("kaptcha.textproducer.font.size","32");
        /*颜色*/
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
        /*字符串范围*/
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        /*字符长度*/
        properties.setProperty("kaptcha.textproducer.char.length","4");
        /*干扰类*/
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");


        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

    /*
    使用实例:
    @Autowired
    private Producer kaptchaProducer;

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
    */

}
