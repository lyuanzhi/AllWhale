package com.jumbox.demo.mbg.pojo;

import javax.persistence.*;

@Table(name = "`community_heart`")
public class CommunityHeart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "community_id")
    private String communityId;

    @Column(name = "is_cancelled")
    private Integer isCancelled;

    @Column(name = "create_time")
    private Long createTime;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return community_id
     */
    public String getCommunityId() {
        return communityId;
    }

    /**
     * @param communityId
     */
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
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
}