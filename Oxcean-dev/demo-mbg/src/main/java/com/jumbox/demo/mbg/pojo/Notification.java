package com.jumbox.demo.mbg.pojo;

import javax.persistence.*;

@Table(name = "`notification`")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "from_name")
    private String fromName;

    @Column(name = "to_name")
    private String toName;

    @Column(name = "goods_id")
    private String goodsId;

    @Column(name = "activity_id")
    private String activityId;

    private String message;

    @Column(name = "img_number")
    private Integer imgNumber;

    @Column(name = "is_checked")
    private Integer isChecked;

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
     * @return from_name
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * @param fromName
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * @return to_name
     */
    public String getToName() {
        return toName;
    }

    /**
     * @param toName
     */
    public void setToName(String toName) {
        this.toName = toName;
    }

    /**
     * @return goods_id
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return activity_id
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * @param activityId
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
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
     * @return is_checked
     */
    public Integer getIsChecked() {
        return isChecked;
    }

    /**
     * @param isChecked
     */
    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
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