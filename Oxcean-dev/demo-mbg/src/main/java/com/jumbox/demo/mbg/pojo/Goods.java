package com.jumbox.demo.mbg.pojo;

import javax.persistence.*;

@Table(name = "`goods`")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "wx_number")
    private String wxNumber;

    private String name;

    @Column(name = "detailed_desc")
    private String detailedDesc;

    private Double price;

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(name = "img_number")
    private Integer imgNumber;

    /**
     * 二手类型：闲置、租房
     */
    @Column(name = "second_hand_type")
    private String secondHandType;

    /**
     * 商品类型：美妆、日用、厨具、电子、其他
     */
    @Column(name = "goods_type")
    private String goodsType;

    /**
     * 新旧：全新、几乎全新、略微使用、日常折旧
     */
    @Column(name = "how_new_type")
    private String howNewType;

    /**
     * 取货：自取、上门
     */
    @Column(name = "pick_up_type")
    private String pickUpType;

    /**
     * 位置：市中心、Cowley、Summertown、Botley、Hinksey、其他
     */
    @Column(name = "location_type")
    private String locationType;

    /**
     * 房型：Flat、Studio、House
     */
    @Column(name = "house_type")
    private String houseType;

    /**
     * 是否独卫：独卫、非独卫
     */
    @Column(name = "ensuite_type")
    private String ensuiteType;

    /**
     * 价格/周：<£150、£150-£200、£200-£250、>£250
     */
    @Column(name = "price_type")
    private String priceType;

    /**
     * 租期：<7天、7天-1个月、1-3月、>3月
     */
    @Column(name = "tenancy_type")
    private String tenancyType;

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
     * @return wx_number
     */
    public String getWxNumber() {
        return wxNumber;
    }

    /**
     * @param wxNumber
     */
    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return detailed_desc
     */
    public String getDetailedDesc() {
        return detailedDesc;
    }

    /**
     * @param detailedDesc
     */
    public void setDetailedDesc(String detailedDesc) {
        this.detailedDesc = detailedDesc;
    }

    /**
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return old_price
     */
    public Double getOldPrice() {
        return oldPrice;
    }

    /**
     * @param oldPrice
     */
    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
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
     * 获取二手类型：闲置、租房
     *
     * @return second_hand_type - 二手类型：闲置、租房
     */
    public String getSecondHandType() {
        return secondHandType;
    }

    /**
     * 设置二手类型：闲置、租房
     *
     * @param secondHandType 二手类型：闲置、租房
     */
    public void setSecondHandType(String secondHandType) {
        this.secondHandType = secondHandType;
    }

    /**
     * 获取商品类型：美妆、日用、厨具、电子、其他
     *
     * @return goods_type - 商品类型：美妆、日用、厨具、电子、其他
     */
    public String getGoodsType() {
        return goodsType;
    }

    /**
     * 设置商品类型：美妆、日用、厨具、电子、其他
     *
     * @param goodsType 商品类型：美妆、日用、厨具、电子、其他
     */
    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * 获取新旧：全新、几乎全新、略微使用、日常折旧
     *
     * @return how_new_type - 新旧：全新、几乎全新、略微使用、日常折旧
     */
    public String getHowNewType() {
        return howNewType;
    }

    /**
     * 设置新旧：全新、几乎全新、略微使用、日常折旧
     *
     * @param howNewType 新旧：全新、几乎全新、略微使用、日常折旧
     */
    public void setHowNewType(String howNewType) {
        this.howNewType = howNewType;
    }

    /**
     * 获取取货：自取、上门
     *
     * @return pick_up_type - 取货：自取、上门
     */
    public String getPickUpType() {
        return pickUpType;
    }

    /**
     * 设置取货：自取、上门
     *
     * @param pickUpType 取货：自取、上门
     */
    public void setPickUpType(String pickUpType) {
        this.pickUpType = pickUpType;
    }

    /**
     * 获取位置：市中心、Cowley、Summertown、Botley、Hinksey、其他
     *
     * @return location_type - 位置：市中心、Cowley、Summertown、Botley、Hinksey、其他
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * 设置位置：市中心、Cowley、Summertown、Botley、Hinksey、其他
     *
     * @param locationType 位置：市中心、Cowley、Summertown、Botley、Hinksey、其他
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /**
     * 获取房型：Flat、Studio、House
     *
     * @return house_type - 房型：Flat、Studio、House
     */
    public String getHouseType() {
        return houseType;
    }

    /**
     * 设置房型：Flat、Studio、House
     *
     * @param houseType 房型：Flat、Studio、House
     */
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    /**
     * 获取是否独卫：独卫、非独卫
     *
     * @return ensuite_type - 是否独卫：独卫、非独卫
     */
    public String getEnsuiteType() {
        return ensuiteType;
    }

    /**
     * 设置是否独卫：独卫、非独卫
     *
     * @param ensuiteType 是否独卫：独卫、非独卫
     */
    public void setEnsuiteType(String ensuiteType) {
        this.ensuiteType = ensuiteType;
    }

    /**
     * 获取价格/周：<£150、£150-£200、£200-£250、>£250
     *
     * @return price_type - 价格/周：<£150、£150-£200、£200-£250、>£250
     */
    public String getPriceType() {
        return priceType;
    }

    /**
     * 设置价格/周：<£150、£150-£200、£200-£250、>£250
     *
     * @param priceType 价格/周：<£150、£150-£200、£200-£250、>£250
     */
    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    /**
     * 获取租期：<7天、7天-1个月、1-3月、>3月
     *
     * @return tenancy_type - 租期：<7天、7天-1个月、1-3月、>3月
     */
    public String getTenancyType() {
        return tenancyType;
    }

    /**
     * 设置租期：<7天、7天-1个月、1-3月、>3月
     *
     * @param tenancyType 租期：<7天、7天-1个月、1-3月、>3月
     */
    public void setTenancyType(String tenancyType) {
        this.tenancyType = tenancyType;
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