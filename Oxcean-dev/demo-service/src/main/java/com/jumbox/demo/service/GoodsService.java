package com.jumbox.demo.service;

import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.pojo.Goods;
import com.jumbox.demo.mbg.vo.DetailedGoodsVo;
import com.jumbox.demo.mbg.vo.GoodsCollectionVo;
import com.jumbox.demo.mbg.vo.GoodsTypeVo;
import com.jumbox.demo.mbg.vo.MyGoodsVo;

import java.util.List;

public interface GoodsService {

    public PageResult getGoodsList(GoodsTypeVo goodsTypeVo);

    public String addGoods(Goods goods);

    public DetailedGoodsVo getGoodsDetailed(String goodsId, String userId);

    public String changeCollection(String goodsId, String userId, Integer type);

    public Integer addWanted(String goodsId, String userId);

    public PageResult searchGoods(String searchValue, Integer page);

    public String cancelGoods(String id);

    public List<GoodsCollectionVo> getMyCollection(String userId);

    public List<MyGoodsVo> getMyGoods(String userId, String type);

    public String deleteGoods(String id);

}
