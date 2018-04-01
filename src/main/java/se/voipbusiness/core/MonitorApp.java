package se.voipbusiness.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@SpringBootApplication
@ComponentScan
@EnableAsync
public class MonitorApp {

    public static void main(String[] args) {

        System.out.println(args);
        SpringApplication.run(MonitorApp.class, args);

    }
}
