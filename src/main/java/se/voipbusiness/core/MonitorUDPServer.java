package se.voipbusiness.core;

import java.io.*;
import java.net.*;

/**
 * Created by espinraf on 18/05/15.
 */
public class MonitorUDPServer extends Thread{

    public Monitor mon = null;
    public int udpPort = Integer.valueOf(System.getProperty("udpServer.port", "9090"));

    @Override
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

                mon.routeToWsServer(sentence);

            }

        }
        catch (Exception ex) {
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