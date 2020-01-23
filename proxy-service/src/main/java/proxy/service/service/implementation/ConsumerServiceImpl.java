package proxy.service.service.implementation;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;
import proxy.service.grpc.contracts.FibonacciRequest;
import proxy.service.grpc.contracts.FibonacciResponse;
import proxy.service.grpc.contracts.FibonacciServiceGrpc;
import proxy.service.service.ConsumerService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Iterator;

@Component
public class ConsumerServiceImpl implements ConsumerService<Integer, Integer> {

    private ManagedChannel managedChannel;
    private FibonacciServiceGrpc.FibonacciServiceBlockingStub stub;
    private Iterator<FibonacciResponse> internalState;
    private boolean stateInitialized;

    @PostConstruct
    private void init(){
        // Address name and port could be extracted to properties file
        managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        stub = FibonacciServiceGrpc.newBlockingStub(managedChannel);
    }

    @PreDestroy
    private void preDestroyHandler(){
        managedChannel.shutdown();
    }

    @Override
    public Integer getResult(Integer value) {

        if(!stateInitialized) {
            initializeInternalState(value);
        }

        return internalState.hasNext() ? internalState.next().getChunk() : -1;
    }

    private void initializeInternalState(Integer value){
        FibonacciRequest req = FibonacciRequest
                .newBuilder()
                .setNumber(value)
                .build();

        internalState = stub.getFibonacciSeq(req);

        stateInitialized = true;
    }
}
