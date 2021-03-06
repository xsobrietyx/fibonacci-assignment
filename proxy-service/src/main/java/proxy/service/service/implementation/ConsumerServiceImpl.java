package proxy.service.service.implementation;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import proxy.service.grpc.contracts.FibonacciRequest;
import proxy.service.grpc.contracts.FibonacciResponse;
import proxy.service.grpc.contracts.FibonacciServiceGrpc;
import proxy.service.service.ConsumerService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
@Slf4j
public class ConsumerServiceImpl implements ConsumerService<Integer, Integer> {
    @Value("${proxyService.port}")
    private int proxyServicePort;

    private ManagedChannel managedChannel;
    private FibonacciServiceGrpc.FibonacciServiceBlockingStub stub;

    private static Map<String, Iterator<FibonacciResponse>> cache = new HashMap<>();

    @PostConstruct
    private void init() {
        // Address name and port could be extracted to properties file
        managedChannel = ManagedChannelBuilder
                .forAddress("localhost", proxyServicePort)
                .usePlaintext()
                .build();

        stub = FibonacciServiceGrpc.newBlockingStub(managedChannel);
    }

    @PreDestroy
    private void preDestroyHandler() {
        managedChannel.shutdown();
    }

    @Override
    @NotNull
    public synchronized Integer getResult(Integer value) {
        Iterator<FibonacciResponse> internalState = cache.computeIfAbsent(value.toString(), this::fetchIterator);

        int result = internalState.hasNext() ? internalState.next().getChunk() : -1;

        log.info("action:\"getResult\";from:{};message:Returned {} from service",
                ConsumerServiceImpl.class.getSimpleName(), result);

        return result;
    }

    private Iterator<FibonacciResponse> fetchIterator(String value) {
        FibonacciRequest req = FibonacciRequest
                .newBuilder()
                .setNumber(Integer.parseInt(value))
                .build();

        return stub.getFibonacciSeq(req);
    }
}
