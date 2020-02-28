package proxy.service.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proxy.service.service.ConsumerService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/fibonacci")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class FibonacciEndpoint {

    private final ConsumerService<Integer, Integer> consumerService;

    public FibonacciEndpoint(@Autowired ConsumerService<Integer, Integer> consumerService) {
        this.consumerService = consumerService;
    }

    @GET
    @Path("/{val}")
    public Integer getFibonacci(
            @PathParam("val")
            @NotNull
            @Pattern(regexp = "\\d+")
                    String val) {
        log.info("action:\"getFibonacci\";from:{};message:Received {} from request", FibonacciEndpoint.class.getSimpleName(), val);
        // Min/Max path param constraints could be added, but not necessary in current case
        return consumerService.getResult(Integer.parseInt(val));
    }
}
