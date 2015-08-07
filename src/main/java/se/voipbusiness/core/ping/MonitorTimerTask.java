package se.voipbusiness.core.ping;

import se.voipbusiness.core.Monitor;
import java.util.TimerTask;

/**
 * Created by espinraf on 04/08/15.
 */
public class MonitorTimerTask extends TimerTask {
    String appId;
    public Monitor mon;

    public MonitorTimerTask(String appId, Monitor mon){
        super();
        this.appId = appId;
        this.mon = mon;
    }

    @Override
    public void run(){
        System.out.println("Application down - " + appId);
        mon.sendAppDown(this.appId);

    }
}
