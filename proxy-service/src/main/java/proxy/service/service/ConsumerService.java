package proxy.service.service;

public interface ConsumerService<A, B> {
    B getResult(A value);
}
