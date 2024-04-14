package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Comment;

public class GetCommentListVo {

    private String id;

    private String parentId;

    private String userId;

    private String content;

    private Long createTime;

    private String username;

    private String avatarUrl;

    private Integer heartNum;

    private Integer hasHeart;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
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
    public Integer getHeartNum() { return heartNum; }
    public void setHeartNum(Integer heartNum) { this.heartNum = heartNum; }
    public Integer getHasHeart() { return hasHeart; }
    public void setHasHeart(Integer hasHeart) { this.hasHeart = hasHeart; }

    public static GetCommentListVo POJOtoVO(Comment comment, String username, String avatarUrl,
                                            Integer heartNum, Integer hasHeart) {
        GetCommentListVo getCommentListVo = new GetCommentListVo();
        getCommentListVo.setId(comment.getId());
        getCommentListVo.setParentId(comment.getParentId());
        getCommentListVo.setUserId(comment.getUserId());
        getCommentListVo.setContent(comment.getContent());
        getCommentListVo.setCreateTime(comment.getCreateTime());
        getCommentListVo.setUsername(username);
        getCommentListVo.setAvatarUrl(avatarUrl);
        getCommentListVo.setHeartNum(heartNum);
        getCommentListVo.setHasHeart(hasHeart);
        return getCommentListVo;
    }

}
