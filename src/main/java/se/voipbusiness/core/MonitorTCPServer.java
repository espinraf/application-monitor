package se.voipbusiness.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by espinraf on 14/06/15.
 */
public class MonitorTCPServer {

    public Monitor mon = null;
    public int tcpPort = Integer.valueOf(System.getProperty("tcpServer.port", "9095"));
    public ServerSocket tcpSocket = null;
    public String data;

    public void init(){
        try {
            tcpSocket = new ServerSocket(tcpPort);
            System.out.println("TCP Server started on port: " + tcpPort);
        }
        catch (IOException ex) {
            if (mon != null) {
                mon.logMsg("TCP Server Error: \n");
                mon.logMsg(ex.getMessage());
            }
            else {
                ex.printStackTrace();
            }
        }
    }

    public void run(){

        while(true)
        {
            try {
                Socket connectionSocket = tcpSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                data = inFromClient.readLine();
                mon.routeToWsServer(data);
            }
            catch (IOException ex) {
                if (mon != null) {
                    mon.logMsg("TCP Server Error: \n");
                    mon.logMsg(ex.getMessage());
                }
                else {
                    ex.printStackTrace();
                }
            }

        }
    }
}
