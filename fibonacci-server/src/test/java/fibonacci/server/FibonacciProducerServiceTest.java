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
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FibonacciProducerServiceTest {

    @Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    @Test
    public void getFibonacciSeqTest() throws IOException {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        // Create a server, add service, start, and register for automatic shutdown.
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(new FibonacciProducerServiceImpl())
                .build()
                .start());

        // Create a client channel and register for automatic shutdown.
        FibonacciServiceGrpc.FibonacciServiceBlockingStub blockingStub = FibonacciServiceGrpc
                .newBlockingStub(grpcCleanupRule.register(InProcessChannelBuilder
                                .forName(serverName)
                                .directExecutor()
                                .build()));


        Iterator<FibonacciResponse> reply = blockingStub
                .getFibonacciSeq(FibonacciRequest
                        .newBuilder()
                        .setNumber(17)
                        .build());

        assertEquals(0, reply.next().getChunk());
        assertEquals(1, reply.next().getChunk());
        assertEquals(1, reply.next().getChunk());
        assertEquals(2, reply.next().getChunk());
        assertEquals(3, reply.next().getChunk());
        assertEquals(5, reply.next().getChunk());
        assertEquals(8, reply.next().getChunk());
        assertEquals(13, reply.next().getChunk());
    }

}
