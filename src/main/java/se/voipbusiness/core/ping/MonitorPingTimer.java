package se.voipbusiness.core.ping;

import se.voipbusiness.core.Monitor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by espinraf on 04/08/15.
 */
public class MonitorPingTimer {
    public String appId;
    public String appName;
    public long ttw;
    public Timer timer;
    public Monitor mon;

    public MonitorPingTimer(String appId, String appName, long ttw) {
        this.appId = appId;
        this.appName = appName;
        this.ttw = ttw;
    }

    public void startTimer() {
        TimerTask timerTask = new MonitorTimerTask(this.appId, this.mon );
        timer = new Timer();
        System.out.println("Starting timer for - " + appId);
        timer.schedule(timerTask, this.ttw);
    }


    public void rcvdPing(){
        TimerTask timerTask = new MonitorTimerTask(this.appId, this.mon);
        timer.cancel();
        timer = new Timer();
        System.out.println("Received heartbeat for - " + appId);
        timer.schedule(timerTask, this.ttw);

    }

}
