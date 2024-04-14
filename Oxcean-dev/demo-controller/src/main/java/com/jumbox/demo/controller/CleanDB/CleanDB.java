package com.jumbox.demo.controller.CleanDB;

import com.jumbox.demo.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CleanDB {

    @Resource
    private NotificationService notificationService;

    //每月最后一天
    @Scheduled(cron = "0 0 2 L * ?")
    public void deleteChat(){
        System.out.println("---------定时任务开始执行---------"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
        try{
            notificationService.deleteChat();
        }catch (Exception e){
            System.out.println("清理数据失败，失败原因："+e.getMessage());
        }
        System.out.println("---------定时任务执行成功---------"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

}
