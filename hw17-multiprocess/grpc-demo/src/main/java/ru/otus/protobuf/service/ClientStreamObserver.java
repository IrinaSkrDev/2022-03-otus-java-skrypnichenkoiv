package ru.otus.protobuf.service;


import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.MyResponse;

public class ClientStreamObserver implements StreamObserver<MyResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ClientStreamObserver.class);
    private long lastValue = 0L;

    @Override
    public void onNext(MyResponse value) {
        logger.info("new value ={}", value.getResponseValue());
        setLastValue(value.getResponseValue());
    }

    @Override
    public void onError(Throwable t) {
        logger.error("errors");
    }

    @Override
    public void onCompleted() {
        logger.info("completed...");

    }

    private synchronized void setLastValue(long value) {
        this.lastValue = value;
    }

    public synchronized long getLastValueAndReset() {
        var lastValuePrev = this.lastValue;
        this.lastValue = 0;
        return lastValuePrev;
    }
}
