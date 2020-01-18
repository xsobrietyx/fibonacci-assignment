package fibonacci.server;

import fibonacci.server.service.impl.FibonacciProducerServiceImpl;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import proxy.service.grpc.contracts.FibonacciRequest;
import proxy.service.grpc.contracts.FibonacciResponse;
import proxy.service.grpc.contracts.FibonacciServiceGrpc;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FibonacciProducerServiceTest {

    @Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    @Test
    public void getFibonacciSeqTest() throws IOException {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(new FibonacciProducerServiceImpl())
                .build()
                .start());

        // Create a client channel and register for automatic graceful shutdown.
        FibonacciServiceGrpc.FibonacciServiceBlockingStub blockingStub = FibonacciServiceGrpc
                .newBlockingStub(grpcCleanupRule.register(InProcessChannelBuilder
                                .forName(serverName)
                                .directExecutor()
                                .build()));


        FibonacciResponse reply = blockingStub
                .getFibonacciSeq(FibonacciRequest
                        .newBuilder()
                        .setNumber(17)
                        .build());

        assertEquals("0,1,1,2,3,5,8,13", reply.getMessage());
    }

}
