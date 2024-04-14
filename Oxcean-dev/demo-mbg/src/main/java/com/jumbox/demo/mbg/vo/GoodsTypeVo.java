package com.jumbox.demo.mbg.vo;

public class GoodsTypeVo {

    /**
     * 二手类型：闲置、租房
     */
    private String secondHandType;

    /**
     * 商品类型：美妆、日用、厨具、电子、其他
     */
    private String goodsType;

    /**
     * 新旧：全新、几乎全新、略微使用、日常折旧
     */
    private String howNewType;

    /**
     * 取货：自取、上门
     */
    private String pickUpType;

    /**
     * 位置：市中心、Cowley、Summertown、Botley、Hinksey、其他
     */
    private String locationType;

    /**
     * 房型：Flat、Studio、House
     */
    private String houseType;

    /**
     * 是否独卫：独卫、非独卫
     */
    private String ensuiteType;

    /**
     * 价格/周：<£150、£150-£200、£200-£250、>£250
     */
    private String priceType;

    /**
     * 租期：<7天、7天-1个月、1-3月、>3月
     */
    private String tenancyType;

    private Integer page;

    public String getSecondHandType() {
        return secondHandType;
    }
    public void setSecondHandType(String secondHandType) {
        this.secondHandType = secondHandType;
    }
    public String getGoodsType() {
        return goodsType;
    }
    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
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
    public String getEnsuiteType() {
        return ensuiteType;
    }
    public void setEnsuiteType(String ensuiteType) {
        this.ensuiteType = ensuiteType;
    }
    public String getPriceType() {
        return priceType;
    }
    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
    public String getTenancyType() {
        return tenancyType;
    }
    public void setTenancyType(String tenancyType) {
        this.tenancyType = tenancyType;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }

}
