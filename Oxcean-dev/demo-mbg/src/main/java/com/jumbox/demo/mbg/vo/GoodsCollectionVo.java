package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Goods;
import com.jumbox.demo.mbg.pojo.User;

public class GoodsCollectionVo {

    private String secondHandType;

    private String id;

    private String name;

    private Double price;

    private Double oldPrice;

    private Integer imgNumber;

    private Long createTime;

    private String howNewType;

    private String pickUpType;

    private String locationType;

    private String houseType;

    private String tenancyType;

    private String username;

    private String avatarUrl;

    private Integer isCancelled;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getOldPrice() {
        return oldPrice;
    }
    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }
    public Integer getImgNumber() {
        return imgNumber;
    }
    public void setImgNumber(Integer imgNumber) {
        this.imgNumber = imgNumber;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getHowNewType() {
        return howNewType;
    }
    public void setHowNewType(String howNewType) {
        this.howNewType = howNewType;
    }
    public String getPickUpType() {
        return pickUpType;
    }
    public void setPickUpType(String pickUpType) {
        this.pickUpType = pickUpType;
    }
    public String getLocationType() {
        return locationType;
    }
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }
    public String getHouseType() {
        return houseType;
    }
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
    public String getTenancyType() {
        return tenancyType;
    }
    public void setTenancyType(String tenancyType) {
        this.tenancyType = tenancyType;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public Integer getIsCancelled() {
        return isCancelled;
    }
    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }
    public String getSecondHandType() {
        return secondHandType;
    }
    public void setSecondHandType(String secondHandType) {
        this.secondHandType = secondHandType;
    }

    public static GoodsCollectionVo POJOtoVO(Goods goods, User user) {
        GoodsCollectionVo goodsCollectionVo = new GoodsCollectionVo();
        goodsCollectionVo.setId(goods.getId());
        goodsCollectionVo.setSecondHandType(goods.getSecondHandType());
        goodsCollectionVo.setName(goods.getName());
        goodsCollectionVo.setPrice(goods.getPrice());
        goodsCollectionVo.setOldPrice(goods.getOldPrice());
        goodsCollectionVo.setImgNumber(goods.getImgNumber());
        goodsCollectionVo.setCreateTime(goods.getCreateTime());
        goodsCollectionVo.setHowNewType(goods.getHowNewType());
        goodsCollectionVo.setPickUpType(goods.getPickUpType());
        goodsCollectionVo.setLocationType(goods.getLocationType());
        goodsCollectionVo.setHouseType(goods.getHouseType());
        goodsCollectionVo.setTenancyType(goods.getTenancyType());
        goodsCollectionVo.setUsername(user.getUsername());
        goodsCollectionVo.setAvatarUrl(user.getAvatarUrl());
        goodsCollectionVo.setIsCancelled(goods.getIsCancelled());

        return goodsCollectionVo;
    }

}
