package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Goods;

public class GoodsVo {

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

    public static GoodsVo POJOtoVO(Goods goods) {
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setId(goods.getId());
        goodsVo.setName(goods.getName());
        goodsVo.setPrice(goods.getPrice());
        goodsVo.setOldPrice(goods.getOldPrice());
        goodsVo.setImgNumber(goods.getImgNumber());
        goodsVo.setCreateTime(goods.getCreateTime());
        goodsVo.setHowNewType(goods.getHowNewType());
        goodsVo.setPickUpType(goods.getPickUpType());
        goodsVo.setLocationType(goods.getLocationType());
        goodsVo.setHouseType(goods.getHouseType());
        goodsVo.setTenancyType(goods.getTenancyType());
        return goodsVo;
    }

}
