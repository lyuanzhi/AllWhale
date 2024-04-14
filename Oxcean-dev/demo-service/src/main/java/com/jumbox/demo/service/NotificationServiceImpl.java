package com.jumbox.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumbox.demo.common.idworker.Sid;
import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.mapper.*;
import com.jumbox.demo.mbg.pojo.*;
import com.jumbox.demo.mbg.vo.ChatVo;
import com.jumbox.demo.mbg.vo.GetAccpetList2Vo;
import com.jumbox.demo.mbg.vo.GetChatListVo;
import com.jumbox.demo.mbg.vo.GetCommunityListVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Resource
    NotificationMapper notificationMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    ActivityMapper activityMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    CommunityService communityService;

    @Resource
    CommunityMapper communityMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ChatVo getChat(Integer page, String toName, String fromName, String goodsId, String activityId){
        if(goodsId!=null || activityId!=null){
            Example example2 = new Example(Notification.class);
            example2.createCriteria().andEqualTo("toName", fromName).andEqualTo("fromName", toName);
            Notification notification = new Notification();
            notification.setIsChecked(1);
            notificationMapper.updateByExampleSelective(notification,example2);
        }

        PageHelper.startPage(page, 10);
        Example example = new Example(Notification.class);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("toName", toName).andEqualTo("fromName", fromName);
        Example.Criteria criteria2 = example.createCriteria();
        criteria2.andEqualTo("toName", fromName).andEqualTo("fromName", toName);
        example.or(criteria2);
        example.setOrderByClause("create_time desc");

        List<Notification> notificationList = notificationMapper.selectByExample(example);
        Collections.reverse(notificationList);

        PageInfo<Notification> pageList = new PageInfo<>(notificationList);

        PageResult pagedResult = new PageResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(notificationList);
        pagedResult.setRecords(pageList.getTotal());

        ChatVo chatVo = new ChatVo();
        chatVo.setPagedResult(pagedResult);

        if(goodsId!=null){
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            chatVo.setName(goods.getName());
            chatVo.setPrice(goods.getPrice());
        }
        if(activityId!=null){
            Activity activity = activityMapper.selectByPrimaryKey(activityId);
            chatVo.setTitle(activity.getTitle());
        }
        return chatVo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Notification addChat(Notification notification){
        notification.setId(Sid.next());
        Date date = new Date();
        notification.setCreateTime(date.getTime());
        notification.setIsChecked(0);
        notificationMapper.insert(notification);
        return notification;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteChat(){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, -30);
        Long delete = calendar.getTime().getTime();
        Example example = new Example(Notification.class);
        example.createCriteria().andLessThan("createTime", delete);
        notificationMapper.deleteByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GetChatListVo> getChatList(String userId){
        List<GetChatListVo> getChatListVos = new LinkedList<>();
        Set<Map<String,String>> hashSet = new HashSet<>();
        List<Notification> notificationList2 = new LinkedList<>();

        Example example = new Example(Notification.class);
        example.createCriteria().orEqualTo("toName", userId).orEqualTo("fromName", userId);
        example.setOrderByClause("create_time desc");
        List<Notification> notificationList = notificationMapper.selectByExample(example);

        //去重
        for(Notification notification : notificationList){
            Map<String,String> map = new HashMap<>();
            map.put("toName", notification.getToName());
            map.put("fromName", notification.getFromName());
            Map<String,String> map2 = new HashMap<>();
            map2.put("toName", notification.getFromName());
            map2.put("fromName", notification.getToName());
            if(!hashSet.contains(map)){
                hashSet.add(map);
                hashSet.add(map2);
                notificationList2.add(notification);
            }
        }

        for (Notification notification : notificationList2){
            User user;
            int isYours;
            if(notification.getFromName().equals(userId)){
                isYours = 1;
                user = userMapper.selectByPrimaryKey(notification.getToName());
            }else{
                isYours = 0;
                user = userMapper.selectByPrimaryKey(notification.getFromName());
            }
            String title = null;
            if(notification.getActivityId()!=null){
                title = activityMapper.selectByPrimaryKey(notification.getActivityId()).getTitle();
            }
            getChatListVos.add(GetChatListVo.POJOtoVO(notification, user, isYours, title));
        }

        return getChatListVos;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer getShowRed(String userId){
        Example example = new Example(Notification.class);
        example.createCriteria().andEqualTo("toName",userId).andEqualTo("isChecked",0);
        int flag = 0;
        if(notificationMapper.selectCountByExample(example)!=0){
            flag = 1;
        }
        Example example1 = new Example(Community.class);
        example1.createCriteria().andEqualTo("userId",userId).andEqualTo("isCancelled",0);
        List<Community> communityList = communityMapper.selectByExample(example1);
        int flag1 = 0;
        for (Community community : communityList){
            Example example2 = new Example(Comment.class);
            example2.createCriteria().andEqualTo("communityId", community.getId()).andEqualTo("isCancelled",0)
                    .andNotEqualTo("userId", userId).andEqualTo("isChecked",0);
            if(commentMapper.selectCountByExample(example2)>0){
                flag1 = 1;
                break;
            }
        }
        if(flag == 1 || flag1 == 1){
            return 1;
        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String updateIsChecked(String id){
        Notification notification = new Notification();
        notification.setId(id);
        notification.setIsChecked(1);
        notificationMapper.updateByPrimaryKeySelective(notification);
        return "success";
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GetAccpetList2Vo> getAccpetList2(String userId){
        List<GetAccpetList2Vo> getAccpetList2VoList = new LinkedList<>();
        List<GetCommunityListVo> getCommunityListVoList = communityService.getMyTopic(userId);
        int i = 0;
        for(GetCommunityListVo getCommunityListVo : getCommunityListVoList){
            i++;
            Example example = new Example(Comment.class);
            example.createCriteria().andEqualTo("communityId", getCommunityListVo.getId()).andEqualTo("isCancelled",0)
                    .andNotEqualTo("userId", userId);
            example.setOrderByClause("create_time desc");
            List<Comment> commentList = commentMapper.selectByExample(example);
            if(commentList.isEmpty()){
                i--;
                continue;
            }
            User user = userMapper.selectByPrimaryKey(commentList.get(0).getUserId());
            GetAccpetList2Vo getAccpetList2Vo = GetAccpetList2Vo.POJOtoVO(getCommunityListVo, commentList.get(0), user);
            getAccpetList2VoList.add(getAccpetList2Vo);
            if(i>=20){
                break;
            }
        }
        return getAccpetList2VoList;
    }

}
