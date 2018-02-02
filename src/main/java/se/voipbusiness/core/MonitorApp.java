package se.voipbusiness.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MonitorApp {
    public static void main(String[] args) {

        System.out.println(args);
        SpringApplication.run(MonitorApp.class, args);

    }
}
