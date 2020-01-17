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

@Component
public class ConsumerServiceImpl implements ConsumerService<Integer, String> {

    private ManagedChannel managedChannel;
    private FibonacciServiceGrpc.FibonacciServiceBlockingStub stub;

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
    public String getResult(Integer value) {

        FibonacciRequest req = FibonacciRequest
                .newBuilder()
                .setNumber(value)
                .build();

        FibonacciResponse helloResponse = stub.getFibonacciSeq(req);

        return helloResponse.getMessage();
    }
}
