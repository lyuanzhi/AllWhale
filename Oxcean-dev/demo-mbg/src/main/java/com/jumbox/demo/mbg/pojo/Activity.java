package com.jumbox.demo.mbg.pojo;

import javax.persistence.*;

@Table(name = "`activity`")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "user_id")
    private String userId;

    private String title;

    private String content;

    @Column(name = "activity_type")
    private String activityType;

    @Column(name = "activity_time")
    private String activityTime;

    @Column(name = "max_people")
    private Integer maxPeople;

    @Column(name = "activity_addr")
    private String activityAddr;

    @Column(name = "activity_fee")
    private Double activityFee;

    @Column(name = "img_number")
    private Integer imgNumber;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "is_cancelled")
    private Integer isCancelled;

    @Column(name = "is_deleted")
    private Integer isDeleted;

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
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return activity_type
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * @param activityType
     */
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    /**
     * @return activity_time
     */
    public String getActivityTime() {
        return activityTime;
    }

    /**
     * @param activityTime
     */
    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    /**
     * @return max_people
     */
    public Integer getMaxPeople() {
        return maxPeople;
    }

    /**
     * @param maxPeople
     */
    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    /**
     * @return activity_addr
     */
    public String getActivityAddr() {
        return activityAddr;
    }

    /**
     * @param activityAddr
     */
    public void setActivityAddr(String activityAddr) {
        this.activityAddr = activityAddr;
    }

    /**
     * @return activity_fee
     */
    public Double getActivityFee() {
        return activityFee;
    }

    /**
     * @param activityFee
     */
    public void setActivityFee(Double activityFee) {
        this.activityFee = activityFee;
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

    /**
     * @return is_deleted
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}