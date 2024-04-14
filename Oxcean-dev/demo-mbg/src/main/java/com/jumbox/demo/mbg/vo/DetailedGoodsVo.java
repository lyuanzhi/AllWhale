package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Goods;
import com.jumbox.demo.mbg.pojo.User;

public class DetailedGoodsVo {

    private String userId;

    private String detailedDesc;

    private String username;

    private String avatarUrl;

    private String ensuiteType;

    private boolean isCollected;

    private Integer wanted;

    private Integer isCancelled;

    private Integer isDeleted;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDetailedDesc() {
        return detailedDesc;
    }

    public void setDetailedDesc(String detailedDesc) {
        this.detailedDesc = detailedDesc;
    }

    public String getEnsuiteType() {
        return ensuiteType;
    }

    public void setEnsuiteType(String ensuiteType) {
        this.ensuiteType = ensuiteType;
    }

    public boolean getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public Integer getWanted() {
        return wanted;
    }

    public void setWanted(Integer wanted) {
        this.wanted = wanted;
    }

    public Integer getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public static DetailedGoodsVo POJOtoVO(Goods goods, User user, boolean isCollected, Integer wanted) {
        DetailedGoodsVo detailedGoodsVo = new DetailedGoodsVo();
        detailedGoodsVo.setUserId(goods.getUserId());
        detailedGoodsVo.setDetailedDesc(goods.getDetailedDesc());
        detailedGoodsVo.setAvatarUrl(user.getAvatarUrl());
        detailedGoodsVo.setUsername(user.getUsername());
        detailedGoodsVo.setEnsuiteType(goods.getEnsuiteType());
        detailedGoodsVo.setIsCollected(isCollected);
        detailedGoodsVo.setWanted(wanted);
        detailedGoodsVo.setIsCancelled(goods.getIsCancelled());
        detailedGoodsVo.setIsDeleted(goods.getIsDeleted());
        return detailedGoodsVo;
    }

}
