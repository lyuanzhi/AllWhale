package com.jumbox.demo.mbg.pojo;

import javax.persistence.*;

@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String username;

    /**
     * 头像url
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

    @Column(name = "is_cancelled")
    private Integer isCancelled;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取头像url
     *
     * @return avatar_url - 头像url
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 设置头像url
     *
     * @param avatarUrl 头像url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * @return create_time
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return is_cancelled
     */
    public Integer getIsCancelled() {
        return isCancelled;
    }

    /**
     * @param isCancelled
     */
    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }
}