package com.lsy.community.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @Author : Lo Shu-ngan
 * @Classname WkConfig
 * @Description TODO
 * @Date 2020/05/17 01:07
 */
@Configuration
@Slf4j
public class WkConfig {
    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @PostConstruct
    public void init(){
        //创建wk图片目录
        File file = new File(wkImageStorage);
        if (!file.exists()){
            file.mkdir();
            log.info("创建wk图形目录:" + wkImageStorage);
        }
    }
}
