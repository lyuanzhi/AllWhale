package com.jumbox.demo.service;

import com.jumbox.demo.mbg.pojo.User;

import java.util.List;

public interface UserService {

    public User login(String id, String username, String avatarUrl);

    public String disable(String userId, Integer code);

    public Integer getDisable(String userId);

    public List<User> getDisabledUsers();

}
