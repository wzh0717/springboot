//package com.helloworld.demo.task;
//
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//
///**
// * @Description:
// * @author: created by wangzh 2018/05/05 17:49
// **/
//public class QuartzManager {
//
//    /**
//     * @param jobName          任务名
//     * @param jobGroupName     任务组名
//     * @param triggerName      触发器名
//     * @param triggerGroupName 触发器组名
//     * @param jobClass         任务
//     * @param time             时间设置，参考quartz说明文档
//     * @Description:添加任务
//     * @Author: created by wangzh 2018/05/05
//     */
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static void addJob(String jobName, String jobGroupName,
//                              String triggerName, String triggerGroupName, Class jobClass, String time) {
//        try {
//            Scheduler scheduler = getScheduler();
//            // 任务名，任务组，任务执行类
//            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
//            // 触发器
//            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
//                    .withIdentity(triggerName, triggerGroupName)
//                    .withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
//            scheduler.scheduleJob(jobDetail, cronTrigger);
//            // 启动
//            if (!scheduler.isShutdown()) {
//                scheduler.start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * @param jobName          任务名
//     * @param jobGroupName     任务组名
//     * @param triggerName      触发器名
//     * @param triggerGroupName 触发器组名
//     * @Description:删除任务
//     * @Author: created by wangzh 2018/05/05
//     */
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static void removeJob(String jobName, String jobGroupName,
//                                 String triggerName, String triggerGroupName) {
//        try {
//            Scheduler scheduler = getScheduler();
//            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
//
//            scheduler.pauseTrigger(triggerKey);// 停止触发器
//            scheduler.unscheduleJob(triggerKey);// 移除触发器
//            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * @param triggerName      触发器名
//     * @param triggerGroupName 触发器组名s
//     * @param time             时间设置，参考quartz说明文档
//     * @Description: 修改定时任务
//     * @Author: created by wangzh
//     */
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
//        try {
//            Scheduler scheduler = getScheduler();
//            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
//            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
//            if (cronTrigger == null) {
//                return;
//            }
//
//            String oldTime = cronTrigger.getCronExpression();
//            if (oldTime.equalsIgnoreCase(time)) return;
//
//            cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
//                    .withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
//
//            scheduler.rescheduleJob(triggerKey, cronTrigger);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    /**
//     * @Description:启动所有任务
//     * @Author: created by wangzh
//     */
//    public static void startJobs() {
//        try {
//            Scheduler scheduler = getScheduler();
//            scheduler.start();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * @Description:关闭所有任务
//     * @Author: created by wangzh
//     */
//    public static void shutdownJobs() {
//        try {
//            Scheduler scheduler = getScheduler();
//            if (!scheduler.isShutdown()) {
//                scheduler.shutdown();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static Scheduler getScheduler() throws SchedulerException {
//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        return schedulerFactory.getScheduler();
//    }
//}
