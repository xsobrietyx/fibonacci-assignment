package fibonacci.server.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import proxy.service.grpc.contracts.FibonacciRequest;
import proxy.service.grpc.contracts.FibonacciResponse;
import proxy.service.grpc.contracts.FibonacciServiceGrpc;
import scala.collection.Iterator;

import static utilities.FibonacciUtilService.getFibs;

@Slf4j
public class FibonacciProducerServiceImpl extends FibonacciServiceGrpc.FibonacciServiceImplBase {

    @Override
    public void getFibonacciSeq(FibonacciRequest request, StreamObserver<FibonacciResponse> responseObserver) {
        int requestValue = request.getNumber();
        log.info("action:\"getFibonacciSeq\";from:{};message:Requested {} with {}",
                FibonacciProducerServiceImpl.class.getSimpleName(),
                requestValue,
                FibonacciRequest.class.getSimpleName());
        Iterator<Object> i = getFibs(requestValue);

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
