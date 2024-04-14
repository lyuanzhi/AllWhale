package com.jumbox.demo.controller.UserController;

import com.alibaba.fastjson.JSONObject;
import com.jumbox.demo.common.utils.CommonResult;
import com.jumbox.demo.common.utils.UrlUtil;
import com.jumbox.demo.common.wxpay.WechatConstant;
import com.jumbox.demo.service.TrackingService;
import com.jumbox.demo.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/user")
@Api(value = "用户相关接口", tags = {"用户相关接口"})
public class User {

    @Resource
    private UserService userService;

    @Resource
    private TrackingService trackingService;

    @PostMapping(path = "/login")
    public CommonResult<com.jumbox.demo.mbg.pojo.User> login(@RequestParam("jsCode") String jsCode, @RequestParam("username") String username,
                                                             @RequestParam("avatarUrl") String avatarUrl, HttpServletRequest request) {
        Map<String, String> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid", WechatConstant.APP_ID);
        requestUrlParam.put("secret", WechatConstant.SECRET);
        requestUrlParam.put("js_code", jsCode);
        JSONObject jsonObject = JSONObject.parseObject(UrlUtil.sendPost(
                "https://api.weixin.qq.com/sns/jscode2session", requestUrlParam));
        trackingService.AddTrackingInfo(request, jsonObject.getString("openid"), "login");
        return CommonResult.success(userService.login(jsonObject.getString("openid"), username, avatarUrl));
    }

    @PostMapping(path = "/disable")
    public CommonResult<String> disable(@RequestParam("userId") String userId, @RequestParam("code") Integer code) {
        return CommonResult.success(userService.disable(userId, code));
    }

    @PostMapping(path = "/getDisabledUsers")
    public CommonResult<List<com.jumbox.demo.mbg.pojo.User>> getDisabledUsers() {
        return CommonResult.success(userService.getDisabledUsers());
    }

}
