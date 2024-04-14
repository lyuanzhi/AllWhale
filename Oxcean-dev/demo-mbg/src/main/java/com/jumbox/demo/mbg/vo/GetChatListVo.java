package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Notification;
import com.jumbox.demo.mbg.pojo.User;

public class GetChatListVo {

    private String goodsId;

    private String activityId;

    private String title;

    private String message;

    private Integer imgNumber;

    private Integer isChecked;

    private Long createTime;

    private String userId;

    private String username;

    private String avatarUrl;

    private Integer isYours;

    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
    public String getActivityId() {
        return activityId;
    }
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getImgNumber() {
        return imgNumber;
    }
    public void setImgNumber(Integer imgNumber) {
        this.imgNumber = imgNumber;
    }
    public Integer getIsChecked() {
        return isChecked;
    }
    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public Integer getIsYours() {
        return isYours;
    }
    public void setIsYours(Integer isYours) {
        this.isYours = isYours;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public static GetChatListVo POJOtoVO(Notification notification, User user, Integer isYours, String title) {
        GetChatListVo getChatListVo = new GetChatListVo();
        getChatListVo.setUserId(user.getId());
        getChatListVo.setCreateTime(notification.getCreateTime());
        getChatListVo.setImgNumber(notification.getImgNumber());
        getChatListVo.setAvatarUrl(user.getAvatarUrl());
        getChatListVo.setIsChecked(notification.getIsChecked());
        getChatListVo.setGoodsId(notification.getGoodsId());
        getChatListVo.setActivityId(notification.getActivityId());
        getChatListVo.setMessage(notification.getMessage());
        getChatListVo.setUsername(user.getUsername());
        getChatListVo.setIsYours(isYours);
        getChatListVo.setTitle(title);
        return getChatListVo;
    }

}
