package se.voipbusiness.core;

import java.io.IOException;

/**
 * Created by espinraf on 07/07/15.
 */
public class TestMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Monitor mon = new Monitor();
        MonitorTTL ttl = new MonitorTTL();
        ttl.mon = mon;
        ttl.init();
    }
}
