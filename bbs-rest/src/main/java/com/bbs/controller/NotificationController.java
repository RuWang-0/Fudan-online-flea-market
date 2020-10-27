package com.bbs.controller;

import com.bbs.common.base.BaseController;
import com.bbs.common.dto.bbsResult;
import com.bbs.common.entity.Notification;
import com.bbs.common.entity.User;
import com.bbs.service.NotificationService;
import com.bbs.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("通知消息接口")
@RequestMapping("/notification")
@RestController
public class NotificationController extends BaseController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @ApiOperation("获取用户的通知消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户的id",dataType ="int")
    })
    @GetMapping("/{uid}")
    public bbsResult getAllNotification(@PathVariable("uid") Integer uid) {
        bbsResult result = restProcessor(() -> {
            User user = userService.findOne(uid);
            if (user==null) return bbsResult.warn("用户不存在！");
            List<Notification> list = notificationService.findByUser(user);
            return bbsResult.ok(list);
        });
        return result;
    }

    @ApiOperation("删除用户的通知消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户的id",dataType ="int")
    })
    @DeleteMapping("/{uid}")
    public bbsResult deleteAllNotification(@PathVariable("uid") Integer uid){
        bbsResult result = restProcessor(() -> {
            User user = userService.findOne(uid);
            if (user == null) return bbsResult.warn("用户不存在！");
            notificationService.deleteByUser(user);
            return bbsResult.ok();
        });

        return result;
    }

}
