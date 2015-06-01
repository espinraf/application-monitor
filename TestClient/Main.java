import java.io.IOException;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by espinraf on 18/05/15.
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String data = "{ \"Id\" : \"i001\", \"Name\" : \"Customer\", \"Type\" : \"JMS\", \"Status\" : \"NOK\", \"data\": [{\"msgRecv\" : \"1\", \"type\" : \"C\" },{\"msgSend\" : \"5\", \"type\" : \"C\" },{\"Errors\"  : \"0\", \"type\" : \"C\" },{\"ErrorXSLT\": \"1\", \"type\" : \"B\" },{\"LastError\" : \"0\", \"type\" : \"S\" }]}";

        MonitorUDPClient c = new MonitorUDPClient();
        try {
            c.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(true) {
            System.out.println("Enter something here : ");

            try {
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String s = bufferRead.readLine();

                c.sendData(data);

                System.out.println(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
