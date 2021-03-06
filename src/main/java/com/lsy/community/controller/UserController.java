package com.lsy.community.controller;

import com.lsy.community.annotation.LoginRequired;
import com.lsy.community.entity.User;
import com.lsy.community.service.FollowService;
import com.lsy.community.service.LikeService;
import com.lsy.community.service.UserService;
import com.lsy.community.util.CommunityUtil;
import com.lsy.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static com.lsy.community.util.CommunityConstant.ENTITY_TYPE_USER;

/**
 * @Author : Lo Shu-ngan
 * @Classname UserController
 * @Description TODO
 * @Date 2020/04/27 21:38
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    /**
     * 获取个人设置界面
     * @return
     */
    @GetMapping("/setting")
    @LoginRequired
    public String getSettingPage(){
        return "/site/setting";
    }

    /**
     * 设置头像
     * @param headerImage
     * @param model
     * @return
     */
    @PostMapping("/upload")
    @LoginRequired
    public String uploadHeader(MultipartFile headerImage, Model model){
        if (headerImage == null) {
            model.addAttribute("error","您还没有选择图片");
            return "/site/setting";
        }

        //获取文件本来的名字
        String fileName = headerImage.getOriginalFilename();
        //获取文件名的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件的格式不正确");
            return "/site/setting";
        }

        //生成随机的文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        //确定文件存放的路径
        File dest = new File(uploadPath + "/" +fileName);
        try {
            //存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败:" + e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常!",e);
        }

        //更新当前用户头像路径(web访问路径)
        //http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(),headerUrl);

        return "redirect:/index";
    }

    /**
     * 获取头像
     * @param fileName
     * @param response
     */
    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable("fileName")String fileName, HttpServletResponse response){
        //服务器存放路径
        fileName = uploadPath + "/" +fileName;
        //文件的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
            ) {
                byte[] buffer = new byte[1024];
                int b = 0;
                while((b = fis.read(buffer)) != -1){
                    os.write(buffer,0,b);
            }
        } catch (IOException e) {
            log.error("读取头像失败:" + e.getMessage());
        }
    }

    /**
     * 个人主页
     */
    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable("userId")int userId,Model model){
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }
        // 用户基本信息
        model.addAttribute("user",user);
        //点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount",likeCount);
        //关注数量
        Long followeeCount = followService.findFolloweeCount(userId,ENTITY_TYPE_USER);
        model.addAttribute("followeeCount",followeeCount);
        //粉丝数量
        Long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER,userId);
        model.addAttribute("followerCount",followerCount);
        //是否关注
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null){
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(),ENTITY_TYPE_USER,userId);
        }
        model.addAttribute("hasFollowed",hasFollowed);

        return "/site/profile";
    }


}
