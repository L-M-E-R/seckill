/**
 * @program: seckill
 * @description: Quartz配置类
 * @author: Lmer
 * @create: 2022-03-22 07:52
 **/
package com.lmer.seckill.config;

import com.lmer.seckill.quartz.MysqlJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    /**
     * mysql从redis同步数据
     * @return
     */
    @Bean
    public JobDetail mysqlJobDetail(){
        return JobBuilder.newJob(MysqlJob.class)
                .withIdentity("MysqlJob")
                .usingJobData("msg", "mysql从redis同步数据")
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }

    @Bean
    public Trigger printTimeJobTrigger() {
        // 每5秒执行一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(mysqlJobDetail())
                .withIdentity("MysqlJobService")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}
