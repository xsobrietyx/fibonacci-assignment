package proxy.service.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import proxy.service.rest.controller.FibonacciEndpoint;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(FibonacciEndpoint.class);
    }
}
