package com.jumbox.demo.service;

import com.jumbox.demo.mbg.pojo.Notification;
import com.jumbox.demo.mbg.vo.ChatVo;
import com.jumbox.demo.mbg.vo.GetAccpetList2Vo;
import com.jumbox.demo.mbg.vo.GetChatListVo;

import java.util.List;

public interface NotificationService {

    public ChatVo getChat(Integer page, String toName, String fromName, String goodsId, String activityId);

    public Notification addChat(Notification notification);

    public void deleteChat();

    public List<GetChatListVo> getChatList(String userId);

    public Integer getShowRed(String userId);

    public String updateIsChecked(String id);

    public List<GetAccpetList2Vo> getAccpetList2(String userId);

}
