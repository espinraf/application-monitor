package se.voipbusiness.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created by espinraf on 2017-07-20.
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class MonitorWebSocketServer extends SpringBootServletInitializer implements WebSocketConfigurer {

    @Autowired
    protected MonitorWebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/wscomm").setAllowedOrigins("*");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MonitorWebSocketServer.class);
    }

/*
    @Bean
    public WebSocketHandler monitorWebSocketHandler() {
        return new PerConnectionWebSocketHandler(MonitorWebSocketHandler.class);
    }

*/

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}