package byzas.libs.hazzmon.core.configuration;

import byzas.libs.hazzmon.core.configuration.HazzmonCommonConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 16.05.2018
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({HazzmonCommonConfiguration.class})
public @interface EnableHazzmonListener {
}
