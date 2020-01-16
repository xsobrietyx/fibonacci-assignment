package proxy.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proxy.service.service.FibonacciConsumerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/fibonacci")
@Produces(MediaType.APPLICATION_JSON)
public class FibonacciEndpoint {

    private final FibonacciConsumerService fibonacciConsumerService;

    public FibonacciEndpoint(@Autowired FibonacciConsumerService fibonacciConsumerService) {
        this.fibonacciConsumerService = fibonacciConsumerService;
    }

    @GET
    @Path("/{val}")
    public String getFibonacci(@PathParam("val") String val) {
        return fibonacciConsumerService.getResult(Integer.parseInt(val));
    }
}
