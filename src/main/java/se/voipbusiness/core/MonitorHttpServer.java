package se.voipbusiness.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by espinraf on 19/05/15.
 */
@Controller
public class MonitorHttpServer {

    private final Logger log = LoggerFactory.getLogger(MonitorHttpServer.class);

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public String handleStatic() {
        log.info("Serving Static Pages....");

        return "index.html";
    }

}
