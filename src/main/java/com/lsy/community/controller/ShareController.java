package com.lsy.community.controller;

import com.lsy.community.entity.Event;
import com.lsy.community.event.EventProducer;
import com.lsy.community.util.CommunityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.lsy.community.util.CommunityConstant.TOPIC_SHARE;

/**
 * @Author : Lo Shu-ngan
 * @Classname ShareController
 * @Description TODO
 * @Date 2020/05/17 01:13
 */
@Controller
@Slf4j
public class ShareController {
    @Autowired
    private EventProducer eventProducer;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @GetMapping("/share")
    @ResponseBody
    public String share(String htmlUrl){
        // 文件名
        String fileName = CommunityUtil.generateUUID();

        // 异步生成长图
        Event event = new Event()
                .setTopic(TOPIC_SHARE)
                .setData("htmlUrl",htmlUrl)
                .setData("fileName",fileName)
                .setData("suffix",".png");
        eventProducer.fireEvent(event);

        //返回访问路径
        Map<String,Object> map = new HashMap<>();
        map.put("shareUrl",domain + contextPath + "/share/image/" + fileName);
        return CommunityUtil.getJSONString(0,null,map);
    }

    @GetMapping("/share/image/{fileName}")
    public void getShareImage(@PathVariable("fileName")String fileName, HttpServletResponse response){
        if (StringUtils.isBlank(fileName)){
            throw new IllegalArgumentException("文件夹不能为空!");
        }

        response.setContentType("image/png");
        try {
            File file = new File(wkImageStorage + "/" + fileName + ".png");
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            log.error("获取长图失败:" + e.getMessage());
        }
    }
}
