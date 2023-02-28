package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.GRPCClient;
import ru.otus.protobuf.generated.*;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RemoteDBServiceImpl extends RemoteDBServiceGrpc.RemoteDBServiceImplBase {


    private static final Logger logger = LoggerFactory.getLogger(RemoteDBServiceImpl.class);


    @Override
    public void getValue(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        var currentValue = new AtomicLong(request.getFirstValue());

        var executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            var value = currentValue.incrementAndGet();
            var response = MyResponse.newBuilder().setResponseValue(value).build();
            responseObserver.onNext(response);
            if (value == request.getEndValue()) {
                executor.shutdown();
                responseObserver.onCompleted();
                logger.info("end value was sent");
            }
        };
        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
        /*executor.execute(() -> {
            LocalDateTime timeStart = LocalDateTime.now();
            while (Math.abs(ChronoUnit.SECONDS.between(LocalDateTime.now(), timeStart)) <= 2L && Math.abs(ChronoUnit.MINUTES.between(LocalDateTime.now(), timeStart)) < 1
                    && currentValue.get()<= request.getEndValue()
            ) {

                responseObserver.onNext(MyResponse.newBuilder().setResponseValue(currentValue.incrementAndGet()).build());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                logger.info("печатаем наше значение от cервера" );
            }
        });

*/

    }

}
