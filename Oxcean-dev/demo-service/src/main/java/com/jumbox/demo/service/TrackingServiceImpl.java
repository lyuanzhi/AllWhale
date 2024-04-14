package com.jumbox.demo.service;

import com.jumbox.demo.common.idworker.Sid;
import com.jumbox.demo.common.utils.IpUtil;
import com.jumbox.demo.mbg.mapper.EventTrackingMapper;
import com.jumbox.demo.mbg.pojo.EventTracking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class TrackingServiceImpl implements TrackingService {

    @Resource
    private EventTrackingMapper eventTrackingMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void AddTrackingInfo(HttpServletRequest request, String userId, String userEvent) {
        String ip = IpUtil.getIpAddress(request);

        EventTracking eventTracking = new EventTracking();
        eventTracking.setId(Sid.next());
        eventTracking.setUserId(userId);
        eventTracking.setUserEvent(userEvent);
        eventTracking.setUserIp(ip);
        Date date = new Date();
        eventTracking.setCreateTime(date.getTime());

        eventTrackingMapper.insert(eventTracking);
    }

}
