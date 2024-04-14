package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Community;

public class GetCommunityListVo {

    private String id;

    private String userId;

    private String content;

    private Integer isAnonymous;

    private Long createTime;

    private String username;

    private String avatarUrl;

    private Integer commentNum;

    private Integer heartNum;

    private Integer hasHeart;

    private Integer imgNumber;

    private String itsTags;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getIsAnonymous() {
        return isAnonymous;
    }
    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
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
    public Integer getCommentNum() { return commentNum; }
    public void setCommentNum(Integer commentNum) { this.commentNum = commentNum; }
    public Integer getHeartNum() { return heartNum; }
    public void setHeartNum(Integer heartNum) { this.heartNum = heartNum; }
    public Integer getHasHeart() { return hasHeart; }
    public void setHasHeart(Integer hasHeart) { this.hasHeart = hasHeart; }
    public Integer getImgNumber() {
        return imgNumber;
    }
    public void setImgNumber(Integer imgNumber) {
        this.imgNumber = imgNumber;
    }
    public String getItsTags() {
        return itsTags;
    }
    public void setItsTags(String itsTags) {
        this.itsTags = itsTags;
    }

    public static GetCommunityListVo POJOtoVO(Community community, String username, String avatarUrl,
                                              Integer commentNum, Integer heartNum, Integer hasHeart) {
        GetCommunityListVo getCommunityListVo = new GetCommunityListVo();
        getCommunityListVo.setId(community.getId());
        getCommunityListVo.setUserId(community.getUserId());
        getCommunityListVo.setContent(community.getContent());
        getCommunityListVo.setIsAnonymous(community.getIsAnonymous());
        getCommunityListVo.setCreateTime(community.getCreateTime());
        getCommunityListVo.setUsername(username);
        getCommunityListVo.setAvatarUrl(avatarUrl);
        getCommunityListVo.setCommentNum(commentNum);
        getCommunityListVo.setHeartNum(heartNum);
        getCommunityListVo.setHasHeart(hasHeart);
        getCommunityListVo.setImgNumber(community.getImgNumber());
        getCommunityListVo.setItsTags(community.getItsTags());
        return getCommunityListVo;
    }

}
