package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Comment;
import com.jumbox.demo.mbg.pojo.User;

public class GetAccpetList2Vo {

    private Integer isChecked;
    private String avatarUrl;
    private String username;
    private String content;
    private Long createTime;
    private GetCommunityListVo getCommunityListVo;

    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public Integer getIsChecked() {
        return isChecked;
    }
    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public GetCommunityListVo getGetCommunityListVo() {
        return getCommunityListVo;
    }
    public void setGetCommunityListVo(GetCommunityListVo getCommunityListVo) { this.getCommunityListVo = getCommunityListVo; }

    public static GetAccpetList2Vo POJOtoVO(GetCommunityListVo getCommunityListVo, Comment comment, User user) {
        GetAccpetList2Vo getAccpetList2Vo = new GetAccpetList2Vo();
        getAccpetList2Vo.setGetCommunityListVo(getCommunityListVo);
        getAccpetList2Vo.setContent(comment.getContent());
        getAccpetList2Vo.setIsChecked(comment.getIsChecked());
        getAccpetList2Vo.setCreateTime(comment.getCreateTime());
        if(comment.getIsAnonymous()==0) {
            getAccpetList2Vo.setUsername(user.getUsername());
            getAccpetList2Vo.setAvatarUrl(user.getAvatarUrl());
        }
        return getAccpetList2Vo;
    }

}
