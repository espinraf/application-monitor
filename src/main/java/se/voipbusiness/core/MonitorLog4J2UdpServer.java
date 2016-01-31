package se.voipbusiness.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.apache.logging.log4j.core.LogEvent;

/**
 * Created by espinraf on 31/01/16.
 */
public class MonitorLog4J2UdpServer extends Thread {

    public int log4j2UdpPort = Integer.valueOf(System.getProperty("log4j2UdpServer.port", "55000"));
    public Monitor mon = null;
    DatagramSocket serverSocket = null ;
    ObjectInputStream obj = null;
    ByteArrayInputStream bis = null;

    @Override
    public void run() {

        try {
            serverSocket = new DatagramSocket(log4j2UdpPort);


        LogEvent logEvent = null;

        byte[] packet = new byte[4096];

        System.out.println("Log4j2 UDP Server Listening on " + log4j2UdpPort + " .........");
        while (true) {


            DatagramPacket receivePacket = new DatagramPacket(packet, packet.length);
            serverSocket.receive(receivePacket);
            bis = new ByteArrayInputStream(receivePacket.getData());
            obj = new ObjectInputStream(bis);
            logEvent = (LogEvent) obj.readObject();

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            String sentence = logEvent.getMessage().getFormattedMessage();
            System.out.println("Message: ");
            System.out.println(sentence);

            JsonObject jo = JsonObject.readFrom(sentence);
            JsonValue id = jo.get("Id");
            JsonValue joa = jo.get("data");


            JsonValue appId = jo.get("AppId");
            JsonValue appName =  jo.get("AppName");
            JsonValue ttwS = jo.get("ttw");


            if ( (id != null) && (joa != null)) {
                mon.routeToWsServer(sentence);
            }
            else if ((appId != null) && (ttwS != null)){
                long ttw = Long.parseLong(ttwS.asString());
                System.out.println("Sending to Monitor");
                mon.routeToMonitorPing(appId.asString(), appName.asString(), ttw);
            }
            else {
                mon.logMsg("Unknown message received from: " + IPAddress + ":" + port);
            }

        }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                obj.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

