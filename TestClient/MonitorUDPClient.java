import java.io.*;
import java.net.*;

/**
 * Created by espinraf on 18/05/15.
 */
public class MonitorUDPClient {

    DatagramSocket clientSocket;
    byte[] sendData = new byte[1024];
    InetAddress IPAddress;
    String serverHostname;

    public void init(){
        try {
            serverHostname = new String ("127.0.0.1");
            IPAddress = InetAddress.getByName(serverHostname);

            clientSocket = new DatagramSocket();

            System.out.println ("Attemping to connect to " + IPAddress +
                    ") via UDP port 9090");
            }
        catch (UnknownHostException ex) {
            System.err.println(ex);
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void sendData(String data){
        try {
            sendData = data.getBytes();

            System.out.println ("Sending data to " + sendData.length +
                    " bytes to server.");

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, 9090);

            clientSocket.send(sendPacket);
            }
        catch (UnknownHostException ex) {
            System.err.println(ex);
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /*
    public void init() throws Exception
    {
        try {
            String serverHostname = new String ("127.0.0.1");


            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(serverHostname);
            System.out.println ("Attemping to connect to " + IPAddress +
                    ") via UDP port 9090");

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            String data = "{ \"Id\" : \"i001\", \"Name\" : \"Customer\", \"Type\" : \"JMS\", \"Status\" : \"NOK\", \"data\": [{\"msgRecv\" : \"1\", \"type\" : \"C\" },{\"msgSend\" : \"5\", \"type\" : \"C\" },{\"Errors\"  : \"0\", \"type\" : \"C\" },{\"ErrorXSLT\": \"1\", \"type\" : \"B\" },{\"LastError\" : \"0\", \"type\" : \"S\" }]}";
            sendData = data.getBytes();

            System.out.println ("Sending data to " + sendData.length +
                    " bytes to server.");
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, 9090);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);

            System.out.println ("Waiting for return packet");
            clientSocket.setSoTimeout(10000);

            try {
                clientSocket.receive(receivePacket);
                String modifiedSentence =
                        new String(receivePacket.getData());

                InetAddress returnIPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                System.out.println ("From server at: " + returnIPAddress +
                        ":" + port);
                System.out.println("Message: " + modifiedSentence);

            }
            catch (SocketTimeoutException ste)
            {
                System.out.println ("Timeout Occurred: Packet assumed lost");
            }

            clientSocket.close();
        }
        catch (UnknownHostException ex) {
            System.err.println(ex);
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
    */
}
