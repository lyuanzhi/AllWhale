package com.jumbox.demo.controller;

import com.jumbox.demo.common.utils.RedisOperator;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisBasicController {

    @Resource
    public RedisOperator redis;

    public static final String ACCESS_TOKEN = "access_token";

}
