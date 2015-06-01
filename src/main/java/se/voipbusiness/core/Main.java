package se.voipbusiness.core;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by espinraf on 18/05/15.
 *
 * Main calss which starts Monitor Server
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        
        // UDP Server, this could be replace with a TCP Server
        MonitorUDPServer s = new MonitorUDPServer();
        s.start();

        // Websocket server, https://github.com/TooTallNate/Java-WebSocket
        MonitorWebSocket ws = null;
        try {
            ws = new MonitorWebSocket(9099);
            ws.startDebug();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Http Server, native in JDK7
        MonitorHttpServer ht = new MonitorHttpServer();
        try {
            ht.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // DB implementation using MapDB. http://mapdb.org
        // the implementation is in-memory sa far.
        MonitorDB mdb = new MonitorDB();
        mdb.init();

        // This class orchestate all server mentioned above.
        Monitor mon = new Monitor();
        mon.init(ws, s, mdb);
        s.mon = mon;
        ws.mon = mon;

    }
}
