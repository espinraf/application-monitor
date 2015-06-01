package se.voipbusiness.core;

import java.io.*;
import java.net.*;

/**
 * Created by espinraf on 18/05/15.
 */
public class MonitorUDPServer extends Thread{

    public Monitor mon = null;

    @Override
    public void run(){
        try
        {
            DatagramSocket serverSocket = new DatagramSocket(9090);

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

/*                String capitalizedSentence = sentence.toUpperCase();

                sendData = capitalizedSentence.getBytes();

                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,
                                port);

                serverSocket.send(sendPacket);
*/
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("UDP Port 9090 is occupied.");
            //System.exit(1);
        }
    }

}
