package byzas.libs.hazzmon.core.configuration;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.TransformedResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 23.05.2018
 */


@Configuration
public class SinglePageAppConfig implements WebMvcConfigurer {

    private static final String API_PATH = "/hazzmon-api";
    private static final String PATH_PATTERNS = "/**";
    private static final String FRONT_CONTROLLER = "webjars/hazzmon-ui.html";
    private static final String CONTEXT_PATH_PLACEHOLDER = "#context-path#";
    private static final String FRONT_CONTROLLER_ENCODING = StandardCharsets.UTF_8.name();

    private final String contextPath;
    private final ResourceProperties resourceProperties;

    public SinglePageAppConfig(
            @Value("${server.servlet.context-path:hzmnxx}") String contextPath,
            @Value("${server.context-path:hzmnxx}") String contextPathV1,
            ResourceProperties resourceProperties
    ) {
        if("hzmnxx".equals(contextPath) && "hzmnxx".equals(contextPathV1)) this.contextPath = "";
        else if("hzmnxx".equals(contextPath)) this.contextPath = contextPathV1;
        else if("hzmnxx".equals(contextPathV1)) this.contextPath = contextPath;
        else this.contextPath = "";
        this.resourceProperties = resourceProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PATH_PATTERNS)
                .addResourceLocations(resourceProperties.getStaticLocations())
                .resourceChain(true)
                .addResolver(new SinglePageAppResourceResolver());
    }



    private class SinglePageAppResourceResolver extends PathResourceResolver {

        public static final String URL_SEPARATOR = "/";

        private TransformedResource transformedResource(Resource resource) throws IOException {
            String fileContent = IOUtils.toString(resource.getInputStream(), FRONT_CONTROLLER_ENCODING);
            fileContent = fileContent.replace(CONTEXT_PATH_PLACEHOLDER, contextPath + URL_SEPARATOR);
            return new TransformedResource(resource, fileContent.getBytes());
        }

        @Override
        protected Resource getResource(String resourcePath, Resource location) throws IOException {
            Resource resource = location.createRelative(resourcePath);
            if (resource.exists() && resource.isReadable()) {
                //if the asked resource is index.html, we serve it with the base-href rewritten
                if (resourcePath.contains(FRONT_CONTROLLER)) {
                    return transformedResource(resource);
                }

                return resource;
            }

            //do not serve a Resource on an reserved URI
            if ((URL_SEPARATOR + resourcePath).startsWith(API_PATH)) {
                return null;
            }

            //we have just refreshed a page, no ?
            resource = location.createRelative(FRONT_CONTROLLER);
            if (resource.exists() && resource.isReadable()) {
                return transformedResource(resource);
            }

            return null;
        }
    }
}