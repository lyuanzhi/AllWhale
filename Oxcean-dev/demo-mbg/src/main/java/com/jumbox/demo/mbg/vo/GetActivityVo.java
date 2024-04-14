package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Activity;
import com.jumbox.demo.mbg.pojo.User;

public class GetActivityVo {

    private String id;

    private String title;

    private String activityType;

    private String activityTime;

    private String avatarUrl;

    private Integer isCancelled;

    public String getId() {
            return id;
        }
    public void setId(String id) {
            this.id = id;
        }
    public String getTitle() {
            return title;
        }
    public void setTitle(String title) {
            this.title = title;
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
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public Integer getIsCancelled() {
        return isCancelled;
    }
    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }

    public static GetActivityVo POJOtoVO(Activity activity, User user) {
        GetActivityVo getActivityVo = new GetActivityVo();
        getActivityVo.setId(activity.getId());
        getActivityVo.setTitle(activity.getTitle());
        getActivityVo.setActivityType(activity.getActivityType());
        getActivityVo.setActivityTime(activity.getActivityTime());
        getActivityVo.setIsCancelled(activity.getIsCancelled());
        getActivityVo.setAvatarUrl(user.getAvatarUrl());
        return getActivityVo;
    }

}
