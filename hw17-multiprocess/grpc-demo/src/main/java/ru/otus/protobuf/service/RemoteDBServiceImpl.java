package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.protobuf.generated.MyMessage;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RemoteDBServiceImpl extends RemoteDBServiceGrpc.RemoteDBServiceImplBase {

    private final RealDBService realDBService;

    public RemoteDBServiceImpl(RealDBService realDBService) {
        this.realDBService = realDBService;
    }

    @Override
    public void createListOfValue(MyMessage request, StreamObserver<MyMessage> responseObserver) {
        realDBService.createListOfValue(request.getEndValue());
        responseObserver.onNext(MyMessage.newBuilder().setId(0).setEndValue(request.getEndValue()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getValue(Empty request, StreamObserver<MyMessage> responseObserver) {
        List<Long> allValues = realDBService.getValues();

        Long startValue = 0L;
        allValues.forEach(u -> {

            LocalDateTime timeStart = LocalDateTime.now();
            while (Math.abs(ChronoUnit.SECONDS.between(LocalDateTime.now(), timeStart)) <= 2L && Math.abs(ChronoUnit.MINUTES.between(LocalDateTime.now(), timeStart)) < 1) {
                System.out.println(ChronoUnit.SECONDS.between(LocalDateTime.now(), timeStart));
                responseObserver.onNext(MyMessage.newBuilder().setId(u).build());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("печатаем наше значение от cервера" + u);
            }


        });
        responseObserver.onCompleted();
    }

}
