package fibonacci.server.service.impl;

import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;
import proxy.service.grpc.contracts.FibonacciRequest;
import proxy.service.grpc.contracts.FibonacciResponse;
import proxy.service.grpc.contracts.FibonacciServiceGrpc;

import java.util.Date;

public class FibonacciProducerServiceImpl extends FibonacciServiceGrpc.FibonacciServiceImplBase implements BindableService {

    @Override
    public void getFibonacciSeq(FibonacciRequest request, StreamObserver<FibonacciResponse> responseObserver) {
        FibonacciResponse response = FibonacciResponse.newBuilder().setMessage(new Date().toString()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
