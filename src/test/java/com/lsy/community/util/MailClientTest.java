package com.lsy.community.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @Author : Lo Shu-ngan
 * @Classname MailClientTest
 * @Description TODO
 * @Date 2020/04/26 14:25
 */
@SpringBootTest
class MailClientTest {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    void senMail() {
        mailClient.senMail("744494357@qq.com","测试","ASDASDASDA");
    }

    @Test
    void senMailHTML(){
        Context context = new Context();
        context.setVariable("username","sunday");
        String content = templateEngine.process("/mail/activation",context);
        System.out.println(content);
        mailClient.senMail("744494357@qq.com","HTML测试",content);
    }
}