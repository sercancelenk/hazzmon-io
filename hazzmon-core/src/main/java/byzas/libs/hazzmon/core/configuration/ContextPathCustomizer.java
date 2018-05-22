package byzas.libs.hazzmon.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 20.05.2018
 */

@Configuration
public class ContextPathCustomizer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("hazzmon-ui.html")
                .setCachePeriod(0)
                .addResourceLocations("classpath:/META-INF/resources/webjars/hazzmon-ui.html")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        return location.exists() && location.isReadable() ? location : null;
                    }
                });
        registry.addResourceHandler("/webjars/hazzmon-ui/**", "**/webjars/hazzmon-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/hazzmon-ui/");

    }
}
