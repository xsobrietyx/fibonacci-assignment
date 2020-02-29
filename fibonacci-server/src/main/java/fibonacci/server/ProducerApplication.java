package fibonacci.server;

import fibonacci.server.service.impl.FibonacciProducerServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ProducerApplication {
    private static final int GRPC_SERVER_PORT = 8082;

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(GRPC_SERVER_PORT)
                .addService(new FibonacciProducerServiceImpl())
                .build();

        log.info("action:\"main\";from:{};Producer server successfully started on port {}",
                ProducerApplication.class.getSimpleName(), GRPC_SERVER_PORT);

        server.start();
        server.awaitTermination();
    }
}
