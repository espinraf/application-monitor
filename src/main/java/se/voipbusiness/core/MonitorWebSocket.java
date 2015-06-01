package se.voipbusiness.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


/**
 * Created by espinraf on 18/05/15.
 */
public class MonitorWebSocket extends WebSocketServer {

    public Monitor mon = null;

    public MonitorWebSocket( int port ) throws UnknownHostException {
        super( new InetSocketAddress( port ) );
    }

    public MonitorWebSocket( InetSocketAddress address ) {
        super( address );
    }

    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
        mon.updateWebpage(conn);
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " New connection established !");
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
        //this.sendToAll( conn + " has left the room!" );
        System.out.println( conn + " Connection ended!" );
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        //this.sendToAll( message );

        System.out.println( conn + ": " + message );
    }

    //@Override
    public void onFragment( WebSocket conn, Framedata fragment ) {
        System.out.println( "received fragment: " + fragment );
    }


    @Override
    public void onError( WebSocket conn, Exception ex ) {
        ex.printStackTrace();
        if( conn != null ) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    public void startDebug() throws InterruptedException , IOException {
        WebSocketImpl.DEBUG = true;
        this.start();
        System.out.println("Server started on port: 9099");

    }

    /**
     * Sends <var>text</var> to all currently connected WebSocket clients.
     *
     * @param text
     *            The String to send across the network.
     * @throws InterruptedException
     *             When socket related I/O errors occur.
     */
    public void sendToAll( String text ) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                c.send( text );
            }
        }
    }

    public void updateWebPage(WebSocket con, String text ) {
        con.send( text );
    }

}
