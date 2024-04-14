package com.jumbox.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumbox.demo.common.idworker.Sid;
import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.mapper.GoodsMapper;
import com.jumbox.demo.mbg.mapper.UserCollectionMapper;
import com.jumbox.demo.mbg.mapper.UserMapper;
import com.jumbox.demo.mbg.mapper.UserWantedMapper;
import com.jumbox.demo.mbg.pojo.*;
import com.jumbox.demo.mbg.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    UserCollectionMapper userCollectionMapper;

    @Resource
    UserWantedMapper userWantedMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageResult getGoodsList(GoodsTypeVo goodsTypeVo){
        PageHelper.startPage(goodsTypeVo.getPage(), 10);

        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("secondHandType",goodsTypeVo.getSecondHandType());
        if(goodsTypeVo.getSecondHandType().equals("闲置")){
            if(!goodsTypeVo.getGoodsType().equals("全部")){
                criteria.andEqualTo("goodsType",goodsTypeVo.getGoodsType());
            }
            if(!goodsTypeVo.getHowNewType().equals("全部")){
                criteria.andEqualTo("howNewType",goodsTypeVo.getHowNewType());
            }
            if(!goodsTypeVo.getPickUpType().equals("全部")){
                criteria.andEqualTo("pickUpType",goodsTypeVo.getPickUpType());
            }
        }else if(goodsTypeVo.getSecondHandType().equals("租房")){
            if(!goodsTypeVo.getLocationType().equals("全部")){
                criteria.andEqualTo("locationType",goodsTypeVo.getLocationType());
            }
            if(!goodsTypeVo.getHouseType().equals("全部")){
                criteria.andEqualTo("houseType",goodsTypeVo.getHouseType());
            }
            if(!goodsTypeVo.getEnsuiteType().equals("全部")){
                criteria.andEqualTo("ensuiteType",goodsTypeVo.getEnsuiteType());
            }
            if(!goodsTypeVo.getPriceType().equals("全部")){
                criteria.andEqualTo("priceType",goodsTypeVo.getPriceType());
            }
            if(!goodsTypeVo.getTenancyType().equals("全部")){
                criteria.andEqualTo("tenancyType",goodsTypeVo.getTenancyType());
            }
        }else{
            return null;
        }
        criteria.andEqualTo("isCancelled",0);
        example.setOrderByClause("create_time desc");
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        List<GoodsVo> goodsVoList = new LinkedList<>();
        for (Goods goods : goodsList){
            GoodsVo goodsVo = GoodsVo.POJOtoVO(goods);
            goodsVoList.add(goodsVo);
        }

        PageInfo<Goods> pageList = new PageInfo<>(goodsList);

        PageResult pagedResult = new PageResult();
        pagedResult.setPage(goodsTypeVo.getPage());
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(goodsVoList);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String addGoods(Goods goods){
        Date date = new Date();
        goods.setCreateTime(date.getTime());
        goods.setIsCancelled(0);
        goods.setIsDeleted(0);
        goodsMapper.insert(goods);
        return "success";
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public DetailedGoodsVo getGoodsDetailed(String goodsId, String userId){
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        User user = userMapper.selectByPrimaryKey(goods.getUserId());
        Example example = new Example(UserCollection.class);
        example.createCriteria().andEqualTo("goodsId",goodsId).andEqualTo("userId",userId)
                .andEqualTo("isCancelled",0);
        boolean isCollected = false;
        if(userCollectionMapper.selectCountByExample(example)!=0){
            isCollected = true;
        }
        Example example1 = new Example(UserWanted.class);
        example1.createCriteria().andEqualTo("goodsId",goodsId).andEqualTo("isCancelled",0);
        Integer wanted = userWantedMapper.selectCountByExample(example1);

        return DetailedGoodsVo.POJOtoVO(goods, user, isCollected, wanted);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String changeCollection(String goodsId, String userId, Integer type){
        Example example = new Example(UserCollection.class);
        example.createCriteria().andEqualTo("goodsId",goodsId).andEqualTo("userId",userId);
        UserCollection userCollection = new UserCollection();
        Date date = new Date();
        userCollection.setCreateTime(date.getTime());
        if(userCollectionMapper.selectCountByExample(example)==0){
            userCollection.setGoodsId(goodsId);
            userCollection.setUserId(userId);
            userCollection.setId(Sid.next());
            userCollection.setIsCancelled(0);
            userCollectionMapper.insert(userCollection);
        }else{
            userCollection.setIsCancelled(type);
            userCollectionMapper.updateByExampleSelective(userCollection,example);
        }
        return "success";
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer addWanted(String goodsId, String userId){
        Example example = new Example(UserWanted.class);
        example.createCriteria().andEqualTo("goodsId",goodsId).andEqualTo("userId",userId);
        if(userWantedMapper.selectCountByExample(example)==0){
            UserWanted userWanted = new UserWanted();
            userWanted.setId(Sid.next());
            userWanted.setGoodsId(goodsId);
            userWanted.setUserId(userId);
            Date date = new Date();
            userWanted.setCreateTime(date.getTime());
            userWanted.setIsCancelled(0);
            userWantedMapper.insert(userWanted);
            return 1;
        }
        return 0;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageResult searchGoods(String searchValue, Integer page){
        PageHelper.startPage(page, 10);

        Example example = new Example(Goods.class);
        example.createCriteria().andLike("name", "%%" + searchValue + "%%").andEqualTo("isCancelled",0);
        example.setOrderByClause("create_time desc");
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        List<GoodsVo> goodsVoList = new LinkedList<>();
        for (Goods goods : goodsList){
            GoodsVo goodsVo = GoodsVo.POJOtoVO(goods);
            goodsVoList.add(goodsVo);
        }

        PageInfo<Goods> pageList = new PageInfo<>(goodsList);

        PageResult pagedResult = new PageResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(goodsVoList);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String cancelGoods(String id){
        Goods goods = new Goods();
        goods.setId(id);
        goods.setIsCancelled(1);
        goodsMapper.updateByPrimaryKeySelective(goods);
        return "success";
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GoodsCollectionVo> getMyCollection(String userId){
        List<GoodsCollectionVo> goodsCollectionVoList = new LinkedList<>();
        Example example = new Example(UserCollection.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("isCancelled", 0);
        example.setOrderByClause("create_time desc");
        List<UserCollection> userCollectionList = userCollectionMapper.selectByExample(example);
        for (UserCollection userCollection : userCollectionList){
            Goods goods = goodsMapper.selectByPrimaryKey(userCollection.getGoodsId());
            if(goods.getIsDeleted()==0){
                User user = userMapper.selectByPrimaryKey(goods.getUserId());
                GoodsCollectionVo goodsCollectionVo = GoodsCollectionVo.POJOtoVO(goods, user);
                goodsCollectionVoList.add(goodsCollectionVo);
            }
        }
        return goodsCollectionVoList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<MyGoodsVo> getMyGoods(String userId, String type){
        List<MyGoodsVo> myGoodsVoList = new LinkedList<>();
        Example example = new Example(Goods.class);
        example.createCriteria().andEqualTo("userId",userId).andEqualTo("secondHandType",type)
                .andEqualTo("isDeleted",0);
        example.setOrderByClause("create_time desc");
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        for(Goods goods : goodsList){
            Example example1 = new Example(UserWanted.class);
            example1.createCriteria().andEqualTo("goodsId",goods.getId()).andEqualTo("isCancelled",0);
            Integer wanted = userWantedMapper.selectCountByExample(example1);
            MyGoodsVo myGoodsVo = MyGoodsVo.POJOtoVO(goods, wanted);
            myGoodsVoList.add(myGoodsVo);
        }
        return myGoodsVoList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String deleteGoods(String id){
        Goods goods = new Goods();
        goods.setId(id);
        goods.setIsCancelled(1);
        goods.setIsDeleted(1);
        goodsMapper.updateByPrimaryKeySelective(goods);
        return "success";
    }

}
