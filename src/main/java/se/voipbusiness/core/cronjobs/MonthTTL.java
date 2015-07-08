package se.voipbusiness.core.cronjobs;

import org.quartz.*;
import se.voipbusiness.core.Monitor;

import java.util.Date;

/**
 * Created by espinraf on 08/07/15.
 */
public class MonthTTL implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        JobKey jobKey = context.getJobDetail().getKey();
        Monitor mon = (Monitor) data.get("mon");
        System.out.println("MonthTTL: " + jobKey + " executing at " + new Date());
        mon.routeToMonitorDBFromCron("1m");
    }
}
