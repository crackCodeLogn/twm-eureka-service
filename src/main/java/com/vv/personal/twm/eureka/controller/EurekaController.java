package com.vv.personal.twm.eureka.controller;

import com.netflix.discovery.EurekaClient;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.vv.personal.twm.eureka.feign.TribalWarsFeign;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Vivek
 * @since 27/12/20
 */
@RestController("EurekaController")
@RequestMapping("/discovery/rest")
public class EurekaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaController.class);

    @Autowired
    private EurekaClient discoveryClient;

    @Autowired
    private PeerAwareInstanceRegistry registry;

    @Autowired
    private TribalWarsFeign tribalWarsFeign;

    @GetMapping("/discover")
    public String discover() {
        LOGGER.info("Discovery client contacted now!");
        LOGGER.info("Services registered: {}", discoveryClient.getApplications());
        LOGGER.info("registry: {}", registry.getApplications().getRegisteredApplications());
        return registry.getApplications().getRegisteredApplications().toString();
    }

    @GetMapping("/tw/automate/extractVillasInfo")
    public String triggerAutomationForTroops(@RequestParam(defaultValue = "p") String worldType,
                                             @RequestParam(defaultValue = "9") int worldNumber,
                                             @RequestParam(defaultValue = "false") boolean writeBackToMongo) {
        if (registry.getApplications().getRegisteredApplications().stream().filter(application -> application.getName().equalsIgnoreCase("twm-tribalwars-service")).count() >= 1) {
            LOGGER.info("Forwarding req to tw service for data extraction! Params set to {}, {}, {}", worldType, worldNumber, writeBackToMongo);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            String result = tribalWarsFeign.triggerAutomationForTroops(worldType, worldNumber, writeBackToMongo);
            stopWatch.stop();
            LOGGER.info("Received result in {}s", stopWatch.getTime(TimeUnit.SECONDS));
            return result;
        }
        return "Failed to contact twm service!";
    }

}
