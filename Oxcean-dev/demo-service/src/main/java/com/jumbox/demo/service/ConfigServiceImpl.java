package com.jumbox.demo.service;

import com.jumbox.demo.mbg.mapper.FuncConfigMapper;
import com.jumbox.demo.mbg.pojo.FuncConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private FuncConfigMapper funcConfigMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FuncConfig> getConfig(){
        return funcConfigMapper.selectAll();
    }

}
