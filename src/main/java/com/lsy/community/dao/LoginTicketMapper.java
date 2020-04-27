package com.lsy.community.dao;

import com.lsy.community.entity.LoginTicket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author : Lo Shu-ngan
 * @Classname LoginTicketMapper
 * @Description 登录凭证Mapper类
 * @Date 2020/04/27 14:20
 */
public interface LoginTicketMapper {
    @Insert({"insert into login_ticket(user_id,ticket,status,expired) ",
            "values (#{userId} ,#{ticket} ,#{status} ,#{expired} )"})
    @Options(useGeneratedKeys = true , keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({"select id,user_id,ticket,status,expired ","" +
            "from login_ticket where ticket = #{ticket} "})
    LoginTicket selectByTicket(String ticket);

    @Update({"update login_ticket set status = #{status} where ticket = #{ticket}"})
    int updateStatus(String ticket,int status);
}
