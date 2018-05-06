//package com.helloworld.demo.task;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ScheduleJobs {
//
//    private static final Logger logger= LogManager.getLogger(ScheduleJobs.class.getName());
//    /**
//     * 秒] [分] [小时] [日] [月] [周]
//     * created by wangzh
//     */
//    @Scheduled(cron = "0 0/1 * * * ?")
//    public void myTask() {
//        logger.info("定时任务启动。。。。。。");
//        System.out.println("定时任务启动。。。。。。");
//    }
//}
