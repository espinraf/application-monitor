package se.voipbusiness.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.logging.log4j.core.LogEvent;

/**
 * Created by espinraf on 31/01/16.
 */
public class MonitorLog4J2UdpServer extends Thread {

    DatagramSocket serverSocket = null ;
    ObjectInputStream obj = null;
    ByteArrayInputStream bis = null;

    @Override
    public void run() {

        try {
            serverSocket = new DatagramSocket(55000);


        LogEvent logEvent = null;

        byte[] packet = new byte[4096];

        System.out.println("Listening on 55000.........");
        while (true) {


                DatagramPacket receivePacket = new DatagramPacket(packet, packet.length);
                serverSocket.receive(receivePacket);
                bis = new ByteArrayInputStream(receivePacket.getData());
                obj = new ObjectInputStream(bis);
                logEvent = (LogEvent) obj.readObject();
                System.out.println("Message: ");
                System.out.println(logEvent.getMessage().getFormattedMessage());


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

