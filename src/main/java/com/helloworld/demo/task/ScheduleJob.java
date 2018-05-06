//package com.helloworld.demo.task;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.quartz.CronTrigger;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.CronScheduleBuilder;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @Description:
// * @author: created by wangzh 2018/05/06 17:08
// **/
//@Configuration
//@EnableScheduling
//@Component
//public class ScheduleJob {
//
//    private static final Logger logger = LogManager.getLogger(ScheduleJob.class.getName());
//
//    @Resource(name = "jobDetail")
//    private JobDetail jobDetail;
//
//    @Resource(name = "jobTrigger")
//    private CronTrigger cronTrigger;
//
//    @Resource(name = "schedulerTwo")
//    private Scheduler scheduler;
//
//    /*
//     * 每5s执行
//     * */
//    @Scheduled(fixedRate = 5000)
//    public void jobRun() {
//        logger.info("jobRun运行中...");
//        try {
//            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
//            // String currentCron = trigger.getCronExpression();
//            //读取数据库数据的执行时间
//            String searchCron = "0 0/1 * * * ?";
//            if (searchCron == null || searchCron.isEmpty()) return;
//
//            trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey())
//                    .withSchedule(CronScheduleBuilder.cronSchedule(searchCron)).build();
//            //按新的trigger重新设置job执行
//            scheduler.rescheduleJob(cronTrigger.getKey(), trigger);
//
//        } catch (Exception ex) {
//            logger.error("任务运行异常：" + ex.getMessage());
//        }
//    }
//
//}
