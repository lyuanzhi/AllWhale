package com.jumbox.demo.service;

import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.pojo.Activity;
import com.jumbox.demo.mbg.vo.DetailedActivityVo;
import com.jumbox.demo.mbg.vo.GetActivityVo;

import java.util.List;

public interface ActivityService {

    public Activity addActivity(Activity activity);

    public PageResult getActivity(Integer page, String activityType);

    public DetailedActivityVo getActivityDetailed(String activityId, String userId);

    public String changeCollection(String activityId, String userId, Integer type);

    public List<GetActivityVo> getMyActivity(String userId);

    public String cancelActivity(String id);

    public String deleteActivity(String id);

    public List<GetActivityVo> getMyCollection(String userId);

}
