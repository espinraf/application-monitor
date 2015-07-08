package se.voipbusiness.core;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.TimerTask;
import java.util.Date;
import se.voipbusiness.core.cronjobs.*;

/**
 * Created by espinraf on 20/06/15.
 */
public class MonitorTTL {

    public Monitor mon = null;

    SchedulerFactory sf;
    Scheduler sched;

    public void init() {
        sf = new StdSchedulerFactory();

        try
        {
            sched = sf.getScheduler();

            // Each Hour
            JobDetail job0 = JobBuilder.newJob(HourTTL.class)
                    .withIdentity("job0", "group1")
                    .build();
            CronTrigger trigger0 = TriggerBuilder.newTrigger()
                    .withIdentity("triggerHour", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                    .build();

            job0.getJobDataMap().put("mon", mon);
            Date ft = sched.scheduleJob(job0, trigger0);
            System.out.println(job0.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger0.getCronExpression());

            // Each Day
            JobDetail job1 = JobBuilder.newJob(DayTTL.class)
                    .withIdentity("job1", "group1")
                    .build();
            CronTrigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("triggerDay", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                    .build();

            job1.getJobDataMap().put("mon", mon);
            ft = sched.scheduleJob(job1, trigger1);
            System.out.println(job1.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger1.getCronExpression());

            // Each Week
            JobDetail job2 = JobBuilder.newJob(WeekTTL.class)
                    .withIdentity("job2", "group1")
                    .build();
            CronTrigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("triggerWeek", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * SUN"))
                    .build();

            job2.getJobDataMap().put("mon", mon);
            ft = sched.scheduleJob(job2, trigger2);
            System.out.println(job2.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger2.getCronExpression());

            // Each Month
            JobDetail job3 = JobBuilder.newJob(MonthTTL.class)
                    .withIdentity("job3", "group1")
                    .build();
            CronTrigger trigger3 = TriggerBuilder.newTrigger()
                    .withIdentity("triggerMonth", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 * * L * ?"))
                    .build();

            job3.getJobDataMap().put("mon", mon);
            ft = sched.scheduleJob(job3, trigger3);
            System.out.println(job3.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger3.getCronExpression());

            sched.start();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
