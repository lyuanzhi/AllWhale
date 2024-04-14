package com.jumbox.demo.controller;

import com.jumbox.demo.common.wxpay.IWXPayDomain;
import com.jumbox.demo.common.wxpay.WXPayConfig;
import com.jumbox.demo.common.wxpay.WXPayConstants;
import com.jumbox.demo.common.wxpay.WechatConstant;

import java.io.InputStream;

public class MyWXPayConfig extends WXPayConfig{

    @Override
    public String getAppID() {
        return WechatConstant.APP_ID;
    }

    @Override
    public String getMchID() {
        return WechatConstant.MCH_ID;
    }

    @Override
    public String getKey() {
        return WechatConstant.MCH_KEY;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain(){
        return new IWXPayDomain(){
            @Override
            public void report(final String domain, long elapsedTimeMillis, final Exception ex){
                return;
            }
            @Override
            public DomainInfo getDomain(final WXPayConfig config){
                return new DomainInfo(WXPayConstants.DOMAIN_API,true);
            }
        };
    }
}