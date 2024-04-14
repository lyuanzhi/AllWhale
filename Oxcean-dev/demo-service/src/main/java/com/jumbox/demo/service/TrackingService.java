package com.jumbox.demo.service;

import javax.servlet.http.HttpServletRequest;

public interface TrackingService {

    public void AddTrackingInfo(HttpServletRequest request, String userId, String userEvent);

}
