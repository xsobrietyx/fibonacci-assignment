package fibonacci.server;

import fibonacci.server.service.impl.FibonacciProducerServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ProducerApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int port = 8082;
        Server server = ServerBuilder
                .forPort(port)
                .addService(new FibonacciProducerServiceImpl())
                .build();

        log.info("action:\"main\";from:{};Producer server successfully started on port {}\n",
                ProducerApplication.class.getSimpleName(), port);

        server.start();
        server.awaitTermination();
    }
}
