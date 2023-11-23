package com.vv.personal.twm.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.util.TimeZone;

import static com.vv.personal.twm.eureka.constants.Constants.*;

/**
 * @author Vivek
 * @since 16/11/20
 */
@EnableEurekaServer
@EnableFeignClients
@ComponentScan({"com.vv.personal.twm.eureka", "com.vv.personal.twm.ping"})
@SpringBootApplication
public class EurekaServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaServer.class);

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("EST", ZoneId.SHORT_IDS))); //force setting
        SpringApplication.run(EurekaServer.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void firedUpAllCylinders() {
        String host = LOCALHOST;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error("Failed to obtain ip address. ", e);
        }
        String port = environment.getProperty(LOCAL_SPRING_PORT);
        LOGGER.info("'{}' activation is complete! Exact swagger url: {}", environment.getProperty("spring.application.name").toUpperCase(),
                String.format(SWAGGER_UI_URL, host, port));
        LOGGER.info("Eureka server is live and running at '{}'", String.format(HOST_URL, host, port));
    }
}
