package com.vv.personal.twm.eureka.feign;

import com.vv.personal.twm.ping.feign.HealthFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Vivek
 * @since 27/12/20
 */
@FeignClient("TWM-TRIBALWARS-SERVICE")
public interface TribalWarsFeign extends HealthFeign {

    @GetMapping("/tw/triggerAutomation/troops?worldType={worldType}&worldNumber={worldNumber}&writeBackToMongo={writeBackToMongo}")
    String triggerAutomationForTroops(@PathVariable String worldType,
                                      @PathVariable Integer worldNumber,
                                      @PathVariable Boolean writeBackToMongo);
}
