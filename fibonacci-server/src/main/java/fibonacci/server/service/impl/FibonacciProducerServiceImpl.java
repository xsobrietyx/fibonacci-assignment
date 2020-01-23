package fibonacci.server.service.impl;

import io.grpc.stub.StreamObserver;
import proxy.service.grpc.contracts.FibonacciRequest;
import proxy.service.grpc.contracts.FibonacciResponse;
import proxy.service.grpc.contracts.FibonacciServiceGrpc;
import scala.collection.Iterator;

import static utilities.FibonacciUtilService.getFibs;

public class FibonacciProducerServiceImpl extends FibonacciServiceGrpc.FibonacciServiceImplBase {

    @Override
    public void getFibonacciSeq(FibonacciRequest request, StreamObserver<FibonacciResponse> responseObserver) {
        Iterator<Object> i = getFibs(request.getNumber());

        while (i.hasNext()) {
            FibonacciResponse response = FibonacciResponse
                    .newBuilder()
                    .setChunk(Integer.parseInt(i.next().toString()))
                    .build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }
}
