package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.MyRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumberGeneratedServiceGrpc;
import ru.otus.protobuf.service.ClientStreamObserver;

public class GRPCClient {
    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private long value = 0;

    public static void main(String[] args) throws InterruptedException {
        logger.info("start client...");
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = NumberGeneratedServiceGrpc.newStub(channel);

        logger.info("Начнем-с!");
        new GRPCClient().clientAction(stub);
        logger.info("shutdown client");
        channel.shutdown();


    }

    private void clientAction(NumberGeneratedServiceGrpc.NumberGeneratedServiceStub stub) {
        var myRequest = MyRequest.newBuilder().setFirstValue(1).setEndValue(30).build();
        var clientStreamAbserver = new ClientStreamObserver();
        stub.getValue(myRequest, clientStreamAbserver);

        Long currentValue = 0L;
        Long valueFromServer = 0L;
        Long tempValue = 0L;
        for (int i = 0; i < 50; i++) {
            value = value + clientStreamAbserver.getLastValueAndReset() + 1;
            logger.info("currentValue: " + value);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
