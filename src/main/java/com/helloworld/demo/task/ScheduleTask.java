package com.helloworld.demo.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.QuartzJobBean;
//import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @author: created by wangzh 2018/05/05 22:27
 **/
//@Configuration
//@Component
//@EnableScheduling
public class ScheduleTask extends QuartzJobBean {
    private static final Logger logger = LogManager.getLogger(ScheduleTask.class.getName());

//    public void sayHello() throws JobExecutionException {
//        logger.info("更新数据库中的数据："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("quartz task："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
