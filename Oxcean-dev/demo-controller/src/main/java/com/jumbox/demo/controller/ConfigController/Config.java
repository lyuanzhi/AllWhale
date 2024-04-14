package com.jumbox.demo.controller.ConfigController;

import com.jumbox.demo.common.utils.CommonResult;
import com.jumbox.demo.mbg.pojo.FuncConfig;
import com.jumbox.demo.service.ConfigService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/config")
@Api(value = "小程序配置相关接口", tags = {"小程序配置相关接口"})
public class Config {

    @Resource
    private ConfigService configService;

    @PostMapping(path = "/getConfig")
    public CommonResult<List<FuncConfig>> getConfig() {
        return CommonResult.success(configService.getConfig());
    }

}
