package com.jumbox.demo.controller.WebSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.jumbox.demo.mbg.vo.GetCommunityListVo;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocket {

    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    private Session session;
    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session)
    {
        this.username = username;
        this.session = session;
        clients.put(username, this);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("服务端发生了错误"+error.getMessage());
    }

    @OnClose
    public void onClose()
    {
        clients.remove(username);
    }

    @OnMessage
    public void onMessage(String data, Session session)
    {
        try {
            if(data.equals("pingcli")){
                session.getAsyncRemote().sendText("pingdev");
            }else{
                JSONObject jsonObject = JSON.parseObject(data);
                Integer task = jsonObject.getInteger("task");
                if(task==0){
                    String id = jsonObject.getString("id");
                    String toName = jsonObject.getString("toName");
                    String fromName = jsonObject.getString("fromName");
                    String message = jsonObject.getString("message");
                    String goodsId = jsonObject.getString("goodsId");
                    Integer imgNumber = jsonObject.getInteger("imgNumber");
                    String toUsername = jsonObject.getString("username");
                    String avatarUrl = jsonObject.getString("avatarUrl");
                    String activityId = jsonObject.getString("activityId");
                    String title = jsonObject.getString("title");
                    Map<String,Object> map = Maps.newHashMap();
                    map.put("toName",toName);
                    map.put("fromName",fromName);
                    map.put("message",message);
                    Date date = new Date();
                    map.put("createTime",date.getTime());
                    map.put("id", id);
                    map.put("isChecked", 0);
                    map.put("goodsId", goodsId);
                    map.put("imgNumber", imgNumber);
                    map.put("username", toUsername);
                    map.put("avatarUrl", avatarUrl);
                    map.put("isYours", 0);
                    map.put("activityId", activityId);
                    map.put("title", title);
                    map.put("task", task);
                    System.out.println(JSON.toJSONString(map));
                    sendMessageTo(JSON.toJSONString(map), toName);
                }else if(task==1){
                    System.out.println(data);
                    GetCommunityListVo getCommunityListVo = jsonObject.getObject("getCommunityListVo", GetCommunityListVo.class);
                    sendMessageTo(data, getCommunityListVo.getUserId());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void sendMessageTo(String message, String ToUserName) throws IOException {
        for (WebSocket item : clients.values()) {
            if (item.username.equals(ToUserName) ) {
                item.session.getAsyncRemote().sendText(message);
                break;
            }
        }
    }

}

