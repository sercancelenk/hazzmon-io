package byzas.libs.hazzmon.core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 16.05.2018
 */

@Configuration
@ComponentScan(basePackages = {
        "byzas.libs.hazzmon.core.configuration",
        "byzas.libs.hazzmon.core.filter",
        "byzas.libs.hazzmon.core.service",
        "byzas.libs.hazzmon.core.controller",
        "byzas.libs.hazzmon.core.util"
})
public class HazzmonCommonConfiguration {
}
