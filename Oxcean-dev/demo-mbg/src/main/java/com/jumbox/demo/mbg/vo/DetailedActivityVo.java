package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Activity;
import com.jumbox.demo.mbg.pojo.User;

public class DetailedActivityVo {

    private String userId;

    private String title;

    private String content;

    private String activityType;

    private String activityTime;

    private Integer maxPeople;

    private String activityAddr;

    private Double activityFee;

    private Long createTime;

    private Integer isCancelled;

    private String username;

    private String avatarUrl;

    private boolean isCollected;

    private Integer imgNumber;

    private Integer isDeleted;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getActivityType() {
        return activityType;
    }
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
    public String getActivityTime() {
        return activityTime;
    }
    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }
    public Integer getMaxPeople() {
        return maxPeople;
    }
    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }
    public String getActivityAddr() {
        return activityAddr;
    }
    public void setActivityAddr(String activityAddr) {
        this.activityAddr = activityAddr;
    }
    public Double getActivityFee() {
        return activityFee;
    }
    public void setActivityFee(Double activityFee) {
        this.activityFee = activityFee;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public Integer getIsCancelled() {
        return isCancelled;
    }
    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public boolean getIsCollected() {
        return isCollected;
    }
    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }
    public Integer getImgNumber() {
        return imgNumber;
    }
    public void setImgNumber(Integer imgNumber) {
        this.imgNumber = imgNumber;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }

    public static DetailedActivityVo POJOtoVO(Activity activity, User user, boolean isCollected) {
        DetailedActivityVo detailedActivityVo = new DetailedActivityVo();
        detailedActivityVo.setUserId(activity.getUserId());
        detailedActivityVo.setTitle(activity.getTitle());
        detailedActivityVo.setContent(activity.getContent());
        detailedActivityVo.setActivityType(activity.getActivityType());
        detailedActivityVo.setActivityTime(activity.getActivityTime());
        detailedActivityVo.setMaxPeople(activity.getMaxPeople());
        detailedActivityVo.setActivityAddr(activity.getActivityAddr());
        detailedActivityVo.setActivityFee(activity.getActivityFee());
        detailedActivityVo.setCreateTime(activity.getCreateTime());
        detailedActivityVo.setIsCancelled(activity.getIsCancelled());
        detailedActivityVo.setAvatarUrl(user.getAvatarUrl());
        detailedActivityVo.setUsername(user.getUsername());
        detailedActivityVo.setIsCollected(isCollected);
        detailedActivityVo.setImgNumber(activity.getImgNumber());
        detailedActivityVo.setIsDeleted(activity.getIsDeleted());
        return detailedActivityVo;
    }

}
