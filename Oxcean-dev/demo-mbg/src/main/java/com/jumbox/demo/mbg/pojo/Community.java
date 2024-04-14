package com.jumbox.demo.mbg.pojo;

import javax.persistence.*;

@Table(name = "`community`")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "user_id")
    private String userId;

    private String content;

    @Column(name = "is_anonymous")
    private Integer isAnonymous;

    @Column(name = "img_number")
    private Integer imgNumber;

    @Column(name = "its_tags")
    private String itsTags;

    @Column(name = "create_time")
    private Long createTime;

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
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return is_anonymous
     */
    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    /**
     * @param isAnonymous
     */
    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    /**
     * @return img_number
     */
    public Integer getImgNumber() {
        return imgNumber;
    }

    /**
     * @param imgNumber
     */
    public void setImgNumber(Integer imgNumber) {
        this.imgNumber = imgNumber;
    }

    /**
     * @return its_tags
     */
    public String getItsTags() {
        return itsTags;
    }

    /**
     * @param itsTags
     */
    public void setItsTags(String itsTags) {
        this.itsTags = itsTags;
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