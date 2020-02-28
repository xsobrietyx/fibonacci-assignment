package proxy.service.service.implementation;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import proxy.service.grpc.contracts.FibonacciRequest;
import proxy.service.grpc.contracts.FibonacciResponse;
import proxy.service.grpc.contracts.FibonacciServiceGrpc;
import proxy.service.service.ConsumerService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.util.Iterator;

@Component
/*
    Depends on needs and AC's - bean scope could be Session or Application/Singleton
 */
@SessionScope
@Slf4j
public class ConsumerServiceImpl implements ConsumerService<Integer, Integer> {

    private ManagedChannel managedChannel;
    private FibonacciServiceGrpc.FibonacciServiceBlockingStub stub;
    private Iterator<FibonacciResponse> internalState;
    private boolean stateInitialized;

    @PostConstruct
    private void init() {
        // Address name and port could be extracted to properties file
        managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        stub = FibonacciServiceGrpc.newBlockingStub(managedChannel);
    }

    @PreDestroy
    private void preDestroyHandler() {
        managedChannel.shutdown();
    }

    @Override
    public Integer getResult(Integer value) {

        if (!stateInitialized) {
            initializeInternalState(value);
        }

        return internalState.hasNext() ? performResult() : -1;
    }

    @NotNull
    private Integer performResult() {
        int result = internalState.next().getChunk();
        log.info("action:\"performResult\";from:{};message:Returned {} from service", ConsumerServiceImpl.class.getSimpleName(), result);
        return result;
    }

    private void initializeInternalState(Integer value) {
        FibonacciRequest req = FibonacciRequest
                .newBuilder()
                .setNumber(value)
                .build();

        internalState = stub.getFibonacciSeq(req);

        stateInitialized = true;
    }
}
