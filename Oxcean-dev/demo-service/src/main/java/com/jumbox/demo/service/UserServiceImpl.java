package com.jumbox.demo.service;

import com.jumbox.demo.mbg.mapper.UserMapper;
import com.jumbox.demo.mbg.pojo.Community;
import com.jumbox.demo.mbg.pojo.User;
import com.jumbox.demo.mbg.pojo.UserCollection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User login(String id, String username, String avatarUrl) {
        User userCheck = userMapper.selectByPrimaryKey(id);
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setAvatarUrl(avatarUrl);
        Date date = new Date();
        user.setUpdateTime(date.getTime());
        user.setIsCancelled(0);
        if (userCheck == null) {
            user.setCreateTime(date.getTime());
            userMapper.insert(user);
        } else {
            user.setCreateTime(userCheck.getCreateTime());
            userMapper.updateByPrimaryKeySelective(user);
        }
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String disable(String userId, Integer code){
        User user = new User();
        user.setId(userId);
        user.setIsCancelled(code);
        userMapper.updateByPrimaryKeySelective(user);
        return "success";
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer getDisable(String userId){
        User user = new User();
        user.setId(userId);
        return userMapper.selectByPrimaryKey(user).getIsCancelled();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> getDisabledUsers(){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("isCancelled", 1);
        return userMapper.selectByExample(example);
    }

}
