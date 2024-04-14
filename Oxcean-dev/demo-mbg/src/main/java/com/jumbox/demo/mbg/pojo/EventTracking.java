package com.jumbox.demo.mbg.pojo;

import javax.persistence.*;

@Table(name = "`event_tracking`")
public class EventTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_ip")
    private String userIp;

    @Column(name = "user_event")
    private String userEvent;

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
     * @return user_ip
     */
    public String getUserIp() {
        return userIp;
    }

    /**
     * @param userIp
     */
    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    /**
     * @return user_event
     */
    public String getUserEvent() {
        return userEvent;
    }

    /**
     * @param userEvent
     */
    public void setUserEvent(String userEvent) {
        this.userEvent = userEvent;
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