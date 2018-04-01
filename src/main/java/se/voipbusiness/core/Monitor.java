package se.voipbusiness.core;

import com.eclipsesource.json.JsonObject;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import se.voipbusiness.core.ping.MonitorPingTimer;

import java.util.Date;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created by espinraf on 26/05/15.
 */

@Component
public class Monitor {
    @Autowired
    public MonitorWebSocketHandler wsServer;
    @Autowired
    public MonitorUDPServer udpServer;
    @Autowired
    public MonitorDB mdb;
    @Autowired
    public MonitorPing mp;

    public void routeToWsServer(String data){
        String updateData = mdb.updateApp(data);
        System.out.println("Updated JSON: " + updateData);
        wsServer.sendToAll(updateData);
    }

    public void updateWebpage(WebSocketSession ses){
        String data ="";
        ConcurrentNavigableMap map = mdb.getMonitorMap();
        for(Object k   : map.keySet()){
            JsonObject jo = (JsonObject)map.get(k);
            data = jo.toString();
            wsServer.updateWebPage(ses, data );
        }

    }

    public void updateWebpage(){
        String data ="";
        ConcurrentNavigableMap map = mdb.getMonitorMap();
        for(Object k   : map.keySet()){
            JsonObject jo = (JsonObject)map.get(k);
            data = jo.toString();
            wsServer.sendToAll(data);
        }

    }

    public void logMsg(String msg) {
        String jm = "{\"Id\" : \"LOG\", \"Name\" : \"LOG\", \"Type\" : \"LOG\", \"Status\" : \"" + msg + "\"}";
        wsServer.sendToAll(jm);
    }

    public void routeToUdpServer(String data){
        System.out.println("routeToUdpServer: " + data);
    }

    public void routeToMonitorDBFromCron(String ttl){
        Date d = new Date();
        if(!ttl.equals("1h")) {
            logMsg("Reseting counters: " + ttl + " Time: " + d.toString());
        }
        mdb.resetCounters(ttl);
        if(!ttl.equals("1h")) {
            logMsg("Updating counters: " + ttl + " Time: " + d.toString());
        }
        updateWebpage();
    }

    public void routeToMonitorPing(String appId, String appName, long ttw){
        mp.pingReceived(appId, appName, ttw);
    }

    public void sendAppDown(String appId){
        MonitorPingTimer mpt = (MonitorPingTimer) mp.apps.get(appId);
        mpt.timer.cancel();
        mp.removeApp(appId);
        wsServer.sendToAll("{\"AppId\" : \""  + mpt.appId + "\", \"AppName\" : \"" + mpt.appName +  "\", \"Status\" : \"NOK\" }");
        logMsg("Application DOWN: " + mpt.appId + " - " + mpt.appName);
    }

}
