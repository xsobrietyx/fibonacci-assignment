package fibonacci.server.service.impl;

import io.grpc.stub.StreamObserver;
import proxy.service.grpc.contracts.FibonacciRequest;
import proxy.service.grpc.contracts.FibonacciResponse;
import proxy.service.grpc.contracts.FibonacciServiceGrpc;

import static utilities.FibonacciUtilService.getFibs;

public class FibonacciProducerServiceImpl extends FibonacciServiceGrpc.FibonacciServiceImplBase {

    @Override
    public void getFibonacciSeq(FibonacciRequest request, StreamObserver<FibonacciResponse> responseObserver) {
        FibonacciResponse response = FibonacciResponse.newBuilder().setMessage(getFibs(request.getNumber())).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
