package se.voipbusiness.core;

import java.io.*;
import java.net.InetSocketAddress;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.sun.net.httpserver.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;


/**
 * Created by espinraf on 19/05/15.
 */
public class MonitorHttpServer {

    public int httpPort = Integer.valueOf(System.getProperty("httpServer.port", "9000"));
    public JsonArray ja;

    public  void init() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(httpPort), 0);
        HttpContext hc = server.createContext("/monitor", new HttpMonHandler());
/*
        hc.setAuthenticator(new BasicAuthenticator("get") {
                @Override
                public boolean checkCredentials(String user, String pwd) {
                    int s = ja.size();
                    JsonObject jo = null;
                    String juser;
                    String jpasswd;
                    for (int i = 0; i < s; i++) {
                        jo = (JsonObject) ja.get(i).asObject();
                        juser = jo.get("user").asString();
                        jpasswd = jo.get("passwd").asString();
                        if (user.equals(juser) && pwd.equals(jpasswd)){
                            return true;
                        }
                    }
                    return false;
                }
            }
        );
*/
            server.setExecutor(null); // creates a default executor
            System.out.println("HTTP Server started on port: "+httpPort);
            server.start();
        }

        static class HttpMonHandler implements HttpHandler {


        public void handle(HttpExchange t) throws IOException {
            String path = t.getRequestURI().getPath();
            path = "/wwwroot/" + path.replace("/monitor/", "");
            System.out.println("PATH: " + path);
            String response = null;
            byte[] resp = null;
            int len = 0;

            System.out.println("Calling JAR: ");

            resp = readFromJARFileBytes(path);
            len = resp.length;

            if(resp != null){
                t.sendResponseHeaders(200, len);
                OutputStream os = t.getResponseBody();
                os.write(resp);
                os.close();
            }
            else {
                response = "404 NOT FOUND";
                t.sendResponseHeaders(404, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }

        }


        public byte[] readFromJARFileBytes(String filename)

        {
            InputStream is = HttpMonHandler.class.getResourceAsStream(filename);
            byte[] res = null;
            try {
                res = IOUtils.toByteArray(is);
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

    }

}
