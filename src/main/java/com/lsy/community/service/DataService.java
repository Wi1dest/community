package com.lsy.community.service;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname DataService
 * @Description 数据统计
 * @Date 2020/05/15 19:20
 */
public interface DataService {
    void recordUV(String ip);

    long calculateUV(Date start,Date end);

    void recordDAU(int userId);

    long calculateDAU(Date start,Date end);
}
