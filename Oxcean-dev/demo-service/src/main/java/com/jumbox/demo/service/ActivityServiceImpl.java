package com.jumbox.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumbox.demo.common.idworker.Sid;
import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.mapper.ActivityCollectionMapper;
import com.jumbox.demo.mbg.mapper.ActivityMapper;
import com.jumbox.demo.mbg.mapper.UserMapper;
import com.jumbox.demo.mbg.pojo.*;
import com.jumbox.demo.mbg.vo.DetailedActivityVo;
import com.jumbox.demo.mbg.vo.GetActivityVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ActivityCollectionMapper activityCollectionMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Activity addActivity(Activity activity){
        activity.setId(Sid.next());
        Date date = new Date();
        activity.setCreateTime(date.getTime());
        activity.setIsCancelled(0);
        activity.setIsDeleted(0);
        activityMapper.insert(activity);
        return activity;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageResult getActivity(Integer page, String activityType){
        PageHelper.startPage(page, 10);
        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isCancelled",0);
        if(!activityType.equals("全部")){
            criteria.andEqualTo("activityType", activityType);
        }
        example.setOrderByClause("create_time desc");
        List<Activity> activityList = activityMapper.selectByExample(example);
        List<GetActivityVo> getActivityVoList = new LinkedList<>();
        for(Activity activity : activityList){
            User user = userMapper.selectByPrimaryKey(activity.getUserId());
            GetActivityVo getActivityVo = GetActivityVo.POJOtoVO(activity, user);
            getActivityVoList.add(getActivityVo);
        }
        PageInfo<Activity> pageList = new PageInfo<>(activityList);

        PageResult pagedResult = new PageResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(getActivityVoList);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public DetailedActivityVo getActivityDetailed(String activityId, String userId){
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        User user = userMapper.selectByPrimaryKey(activity.getUserId());
        Example example = new Example(ActivityCollection.class);
        example.createCriteria().andEqualTo("activityId",activityId).andEqualTo("userId",userId)
                .andEqualTo("isCancelled",0);
        boolean isCollected = false;
        if(activityCollectionMapper.selectCountByExample(example)!=0){
            isCollected = true;
        }
        return DetailedActivityVo.POJOtoVO(activity, user, isCollected);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String changeCollection(String activityId, String userId, Integer type){
        Example example = new Example(ActivityCollection.class);
        example.createCriteria().andEqualTo("activityId",activityId).andEqualTo("userId",userId);
        ActivityCollection activityCollection = new ActivityCollection();
        Date date = new Date();
        activityCollection.setCreateTime(date.getTime());
        if(activityCollectionMapper.selectCountByExample(example)==0){
            activityCollection.setActivityId(activityId);
            activityCollection.setUserId(userId);
            activityCollection.setId(Sid.next());
            activityCollection.setIsCancelled(0);
            activityCollectionMapper.insert(activityCollection);
        }else{
            activityCollection.setIsCancelled(type);
            activityCollectionMapper.updateByExampleSelective(activityCollection, example);
        }
        return "success";
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GetActivityVo> getMyActivity(String userId){
        Example example = new Example(Activity.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("isDeleted",0);
        example.setOrderByClause("create_time desc");
        List<Activity> activityList = activityMapper.selectByExample(example);
        List<GetActivityVo> getActivityVoList = new LinkedList<>();
        for(Activity activity : activityList){
            User user = userMapper.selectByPrimaryKey(activity.getUserId());
            GetActivityVo getActivityVo = GetActivityVo.POJOtoVO(activity, user);
            getActivityVoList.add(getActivityVo);
        }
        return getActivityVoList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String cancelActivity(String id){
        Activity activity = new Activity();
        activity.setId(id);
        activity.setIsCancelled(1);
        activityMapper.updateByPrimaryKeySelective(activity);
        return "success";
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String deleteActivity(String id){
        Activity activity = new Activity();
        activity.setId(id);
        activity.setIsCancelled(1);
        activity.setIsDeleted(1);
        activityMapper.updateByPrimaryKeySelective(activity);
        return "success";
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GetActivityVo> getMyCollection(String userId){
        List<GetActivityVo> getActivityVoList = new LinkedList<>();
        Example example = new Example(ActivityCollection.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("isCancelled", 0);
        example.setOrderByClause("create_time desc");
        List<ActivityCollection> activityCollectionList = activityCollectionMapper.selectByExample(example);
        for (ActivityCollection activityCollection : activityCollectionList){
            Activity activity = activityMapper.selectByPrimaryKey(activityCollection.getActivityId());
            if(activity.getIsDeleted()==0){
                User user = userMapper.selectByPrimaryKey(activity.getUserId());
                GetActivityVo getActivityVo = GetActivityVo.POJOtoVO(activity, user);
                getActivityVoList.add(getActivityVo);
            }
        }
        return getActivityVoList;
    }

}
