package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.MyMessage;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = RemoteDBServiceGrpc.newBlockingStub(channel);
        var temp = MyMessage.newBuilder().setEndValue(30L).setId(1L).build();
        var savedUserMsg = stub.createListOfValue(MyMessage.newBuilder().setEndValue(30L).setId(1L).build());

        System.out.printf("Мы задали значение");

        var valueIterator = stub.getValue(Empty.getDefaultInstance());
        System.out.println("Начнем-с!");

        Long currentValue = 0L;
        Long valueFromServer = 0L;
        Long tempValue = 0L;
        for (int i = 0; i <= 50; i++) {
            if (valueIterator.hasNext()) {
                tempValue = valueIterator.next().getId();
                if (i == 0) {
                    System.out.println("Начальное значение valueFromServer : " + tempValue.toString());
                }
            }
            if (valueFromServer == tempValue) {
                currentValue = currentValue + 1;
                System.out.println("currentValue: " + currentValue.toString());
            } else {
                System.out.println("valueFromServer : " + tempValue.toString());
                currentValue = currentValue + tempValue + 1;
                System.out.println("currentValue: " + currentValue.toString());
            }
            valueFromServer = tempValue;
        }
        channel.shutdown();


    }
}
