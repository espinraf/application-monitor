package se.voipbusiness.core;

import org.springframework.stereotype.Component;
import se.voipbusiness.core.ping.MonitorPingTimer;

import java.util.HashMap;

/**
 * Created by espinraf on 04/08/15.
 */
@Component
public class MonitorPing {

    public Monitor mon;
    public HashMap<String, Object> apps = new HashMap<String, Object>();
    public MonitorPingTimer mpt;

    public void removeApp(String appId){
        apps.put(appId, null);
        apps.remove(appId);
    }

    public void pingReceived(String appId, String appName, long ttw){
        MonitorPingTimer mptTemp;
        if ( apps.get(appId) == null){
            mpt = new MonitorPingTimer(appId, appName, ttw);
            mpt.mon = mon;
            apps.put(appId, mpt);
            mpt.startTimer();
            mon.wsServer.sendToAll("{\"AppId\" : \"" + mpt.appId + "\", \"AppName\" : \"" + mpt.appName + "\", \"Status\" : \"OK\" }");
            mon.logMsg("Application UP: " + mpt.appId + " - " + mpt.appName);
        }
        else {
            mptTemp = (MonitorPingTimer) apps.get(appId);
            mptTemp.rcvdPing();
            mon.wsServer.sendToAll("{\"AppId\" : \""  + mpt.appId + "\", \"AppName\" : \"" + mpt.appName +  "\", \"Status\" : \"OK\" }");
        }

    }

}
