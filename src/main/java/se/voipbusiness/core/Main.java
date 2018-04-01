package se.voipbusiness.core;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by espinraf on 18/05/15.
 *
 * Main calss which starts Monitor Server
 */

@Component
public class Main {

    // UDP Server, this could be replace with a TCP Server
    @Autowired
    MonitorUDPServer s;
    // Log4j2 UDP Server, this could be replace with a TCP Server
    @Autowired
    MonitorLog4J2UdpServer l;
    // Websocket server, https://github.com/TooTallNate/Java-WebSocket
    @Autowired
    MonitorWebSocketServer ws;
    // Http Server, native in JDK7
    @Autowired
    MonitorHttpServer ht;
    // This class orchestate all server mentioned above.
    @Autowired
    Monitor mon;
    // DB implementation using MapDB. http://mapdb.org
    // the implementation is in-memory sa far.
    @Autowired
    MonitorDB mdb;
    //Intilize Crons to reset counters
    @Autowired
    MonitorTTL ttl;
    //Initialize Ping (Heartbeat)
    @Autowired
    MonitorPing mp;

@PostConstruct
    void mainStart() throws IOException, InterruptedException {

        // Read Configuration
        String jFile;
        BufferedReader br = new BufferedReader(new FileReader("config/app-monitor.json"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            jFile = sb.toString();
        } finally {
            br.close();
        }

        JsonArray jo = JsonArray.readFrom(jFile);
        System.out.println(jo);

        System.out.println(s);


    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        Thread t1 = new Thread(s);
        t1.start();

        Thread t2 = new Thread(l);
        t2.start();

    }
}
