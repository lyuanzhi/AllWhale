package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.mbg.pojo.Goods;

public class MyGoodsVo {

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

    private Integer wanted;

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

    public static MyGoodsVo POJOtoVO(Goods goods, Integer wanted) {
        MyGoodsVo myGoodsVo = new MyGoodsVo();
        myGoodsVo.setId(goods.getId());
        myGoodsVo.setName(goods.getName());
        myGoodsVo.setPrice(goods.getPrice());
        myGoodsVo.setOldPrice(goods.getOldPrice());
        myGoodsVo.setImgNumber(goods.getImgNumber());
        myGoodsVo.setCreateTime(goods.getCreateTime());
        myGoodsVo.setHowNewType(goods.getHowNewType());
        myGoodsVo.setPickUpType(goods.getPickUpType());
        myGoodsVo.setLocationType(goods.getLocationType());
        myGoodsVo.setHouseType(goods.getHouseType());
        myGoodsVo.setTenancyType(goods.getTenancyType());
        myGoodsVo.setWanted(wanted);
        myGoodsVo.setIsCancelled(goods.getIsCancelled());
        return myGoodsVo;
    }

}
