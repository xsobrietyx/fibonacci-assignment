package fibonacci.server;

import fibonacci.server.service.impl.FibonacciProducerServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ProducerApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(8082)
                .addService(new FibonacciProducerServiceImpl()).build();

        server.start();
        server.awaitTermination();
    }
}
