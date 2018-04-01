package se.voipbusiness.core;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.*;

/**
 * Created by espinraf on 18/05/15.
 */

@Component
public class MonitorUDPServer implements Runnable{

    @Autowired
    public Monitor mon;

    public int udpPort = Integer.valueOf(System.getProperty("udpServer.port", "9090"));

    public void run(){
        try
        {
            DatagramSocket serverSocket = new DatagramSocket(udpPort);
            System.out.println("UDP Server started on port: " + udpPort);

            byte[] receiveData = new byte[1024];
            byte[] sendData  = new byte[1024];

            while(true)
            {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);

                System.out.println ("Waiting for datagram packet");

                serverSocket.receive(receivePacket);

                String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                System.out.println ("From: " + IPAddress + ":" + port);
                System.out.println ("Message: " + sentence);

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

        }
        catch (Exception ex) {
            ex.printStackTrace();
            if (mon != null) {
                mon.logMsg("UDP Server Error: \n");
                mon.logMsg(ex.getMessage());
            }
            else {
                ex.printStackTrace();
            }
        }
    }

}

/*                String capitalizedSentence = sentence.toUpperCase();

                sendData = capitalizedSentence.getBytes();

                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,
                                port);

                serverSocket.send(sendPacket);
*/