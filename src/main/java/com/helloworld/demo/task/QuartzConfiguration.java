package com.helloworld.demo.task;

import org.quartz.*;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
//import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Description:
 * @author: created by wangzh 2018/05/05 22:07
 **/

@Configuration
public class QuartzConfiguration {
    private static String JOB_NAME = "DYNAMIC_JOB";
    private static String TRIGGER_NAME = "DYNAMIC_TRIGGER";
    private static String JOB_GROUP_NAME = "DYNAMIC_JOB_GROUP";
    private static String TRIGGER_GROUP_NAME = "DYNAMIC_TRIGGER_GROUP";

    /**
     * 配置定时任务
     * created by wangzh 2018/05/05
     */
//    @Bean(name = "jobDetail")
//    public MethodInvokingJobDetailFactoryBean detailFactoryBean(ScheduleTask task) {
//        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
//
//        //是否并发执行
//        jobDetail.setConcurrent(false);
//        //任务名称
//        jobDetail.setName(JOB_NAME);
//        //分组
//        jobDetail.setGroup(JOB_GROUP_NAME);
//        jobDetail.setTargetObject(task);
//        jobDetail.setTargetMethod("sayHello");
//        return jobDetail;
//    }

    /*
     * 定时任务的触发器
     * created by wangzh 2018/05/05
     * */
//    @Bean(name = "jobTrigger")
//    public CronTriggerFactoryBean cronJobTrigger(@Qualifier("jobDetail") JobDetail jobDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(jobDetail);
//        // 初始时的cron表达式
//        trigger.setCronExpression("0 0/2 * * * ?");
//        trigger.setName(TRIGGER_NAME);
//        trigger.setGroup(TRIGGER_GROUP_NAME);
//        return trigger;
//    }

    /*
     * 定义quartz调度
     * created by wangzh 2018/05/05
     * */
//    @Bean(name = "schedulerTwo")
//    public SchedulerFactoryBean schedulerFactory(@Qualifier("jobTrigger") Trigger cronJobTrigger) {
//        SchedulerFactoryBean bean = new SchedulerFactoryBean();
//        bean.setOverwriteExistingJobs(true);
//        // 延时启动，应用启动1秒后
//        bean.setStartupDelay(1);
//        // 注册触发器
//        bean.setTriggers(cronJobTrigger);
//        return bean;
//    }
    @Bean
    public JobDetail teatQuartzDetail() {
        return JobBuilder.newJob(ScheduleTask.class).withIdentity("testQuartz").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger() {
        return TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
                .withIdentity("testQuartz")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                .build();
    }
}
