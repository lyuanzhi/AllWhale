package com.jumbox.demo.controller.ChatController;

import com.alibaba.fastjson.JSONObject;
import com.jumbox.demo.common.utils.CommonResult;
import com.jumbox.demo.common.utils.DateUtil;
import com.jumbox.demo.common.utils.UrlUtil;
import com.jumbox.demo.common.wxpay.WechatConstant;
import com.jumbox.demo.controller.RedisBasicController;
import com.jumbox.demo.mbg.pojo.Notification;
import com.jumbox.demo.mbg.vo.*;
import com.jumbox.demo.service.NotificationService;
import com.jumbox.demo.service.TrackingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@Api(value = "聊天相关接口", tags = {"聊天相关接口"})
public class Chat extends RedisBasicController {

    @Resource
    NotificationService notificationService;

    @ApiOperation(value = "获取聊天记录")
    @PostMapping("/getChat")
    public CommonResult<ChatVo> getChat(@RequestParam("page") Integer page, @RequestParam("toName") String toName,
                                        @RequestParam("fromName") String fromName,
                                        @RequestParam(value = "goodsId",required = false) String goodsId,
                                        @RequestParam(value = "activityId",required = false) String activityId) {

        return CommonResult.success(notificationService.getChat(page, toName, fromName, goodsId, activityId));
    }

    @ApiOperation(value = "添加聊天记录")
    @PostMapping("/addChat")
    public CommonResult<Notification> addChat(@RequestBody Notification notification) {

        return CommonResult.success(notificationService.addChat(notification));
    }

    @ApiOperation(value = "消息推送")
    @RequestMapping(value= {"/pushMessage"},method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String pushMessage(HttpServletRequest request, HttpServletResponse response) {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if(isGet){
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            if (DateUtil.checkSignature(signature, timestamp, nonce)) {
                return echostr;
            }
            System.out.println("失败");
        }else{
            try {
                RestTemplate restTemplate = new RestTemplate();
                String fromName = request.getParameter("fromName");
                String toId = request.getParameter("toId");
                WxMssVo wxMssVo = new WxMssVo();
                wxMssVo.setTouser(toId);
                wxMssVo.setTemplate_id("Zdco4YkW9anTkzmccHG_wj3JBpvDCRFsFFDAsqR2Ikc");
                wxMssVo.setPage("pages/index/index");
                Map<String, TemplateData> m = new HashMap<>(2);
                m.put("thing2", new TemplateData("新消息"));
                m.put("thing1", new TemplateData(fromName));
                wxMssVo.setData(m);
                String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
                ResponseEntity<String> responseEntity =
                        restTemplate.postForEntity(url, wxMssVo, String.class);
                return responseEntity.getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getAccessToken() {
        if (redis.get("access_token") != null) {
            return redis.get("access_token");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, String> param = new HashMap<>();
        param.put("grant_type", "client_credential");
        param.put("appid", WechatConstant.APP_ID);
        param.put("secret", WechatConstant.SECRET);
        JSONObject jsonObject = JSONObject.parseObject(UrlUtil.sendPost(url, param));
        redis.set("access_token", jsonObject.getString("access_token"), jsonObject.getLong("expires_in"));
        return jsonObject.getString("access_token");
    }

    @PostMapping("/getChatList")
    public CommonResult<List<GetChatListVo>> getChatList(@RequestParam("userId") String userId) {

        return CommonResult.success(notificationService.getChatList(userId));
    }

    @PostMapping("/getShowRed")
    public CommonResult<Integer> getShowRed(@RequestParam("userId") String userId) {

        return CommonResult.success(notificationService.getShowRed(userId));
    }


    @PostMapping("/updateIsChecked")
    public CommonResult<String> updateIsChecked(@RequestParam("id") String id) {

        return CommonResult.success(notificationService.updateIsChecked(id));
    }

    @PostMapping("/getAccpetList2")
    public CommonResult<List<GetAccpetList2Vo>> getAccpetList2(@RequestParam("userId") String userId) {

        return CommonResult.success(notificationService.getAccpetList2(userId));
    }

    @PostMapping("/checkContent")
    public String checkContent(@RequestParam("userId") String userId, @RequestParam("scene") Integer scene,
                               @RequestParam("content") String content) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                Map<String, Object> m = new HashMap<>(2);
                m.put("version", 2);
                m.put("openid", userId);
                m.put("scene", scene);
                m.put("content", content);
                String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + getAccessToken();
                ResponseEntity<String> responseEntity =
                        restTemplate.postForEntity(url, m, String.class);
                return responseEntity.getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return "failed";
    }

}
