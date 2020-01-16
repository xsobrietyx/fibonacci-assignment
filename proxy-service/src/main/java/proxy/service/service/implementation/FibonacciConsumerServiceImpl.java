package proxy.service.service.implementation;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;
import proxy.service.grpc.contracts.FibonacciRequest;
import proxy.service.grpc.contracts.FibonacciResponse;
import proxy.service.grpc.contracts.FibonacciServiceGrpc;
import proxy.service.service.FibonacciConsumerService;

import javax.annotation.PostConstruct;

@Component
public class FibonacciConsumerServiceImpl implements FibonacciConsumerService {
    /*
        Managed channel could be closed the next way, if necessary:
        managedChannel.shutdown();
     */
    private ManagedChannel managedChannel;
    private FibonacciServiceGrpc.FibonacciServiceBlockingStub stub;

    @PostConstruct
    private void init(){
        // Address name and port could be extracted to properties file
        managedChannel = ManagedChannelBuilder.forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        stub = FibonacciServiceGrpc.newBlockingStub(managedChannel);
    }

    @Override
    public String getResult(int value) {

        FibonacciRequest req = FibonacciRequest.newBuilder().setNumber(5).build();

        FibonacciResponse helloResponse = stub.getFibonacciSeq(req);

        return helloResponse.getMessage();
    }
}
