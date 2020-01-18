package fibonacci.server;

import fibonacci.server.service.impl.FibonacciProducerServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ProducerApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int port = 8082;
        Server server = ServerBuilder
                .forPort(port)
                .addService(new FibonacciProducerServiceImpl())
                .build();
        System.out.printf("%s%s%s\n", "\u001B[32m", "Producer server successfully started on port ", port);
        server.start();
        server.awaitTermination();
    }
}
