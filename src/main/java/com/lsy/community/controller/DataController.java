package com.lsy.community.controller;

import com.lsy.community.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname DataController
 * @Description TODO
 * @Date 2020/05/16 01:21
 */
@Controller
public class DataController {
    @Autowired
    private DataService dataService;

    @RequestMapping("/data")
    public String getDataPage(){
        return "/site/admin/data";
    }

    /**
     * 统计UV
     * @param start
     * @param end
     * @param model
     * @return
     */
    @PostMapping("/data/uv")
    private String getUV(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                         @DateTimeFormat(pattern = "yyyy-MM-dd")Date end,
                         Model model){
        long uv = dataService.calculateUV(start, end);
        model.addAttribute("uvResult",uv);
        model.addAttribute("uvStartDate",start);
        model.addAttribute("uvEndDate",end);

        return "forward:/data";
    }

    /**
     * 统计活跃用户
     * @param start
     * @param end
     * @param model
     * @return
     */
    @PostMapping("/data/dau")
    private String getDAU(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                         @DateTimeFormat(pattern = "yyyy-MM-dd")Date end,
                         Model model){
        long dau = dataService.calculateDAU(start, end);
        model.addAttribute("dauResult",dau);
        model.addAttribute("dauStartDate",start);
        model.addAttribute("dauEndDate",end);

        return "forward:/data";
    }
}
