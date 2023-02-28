package ru.otus.protobuf;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.service.RemoteDBServiceImpl;

import java.io.IOException;

public class GRPCServer {
    private static final Logger logger = LoggerFactory.getLogger(GRPCServer.class);
    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {


        var remoteDBService = new RemoteDBServiceImpl();

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteDBService).build();
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Received shutdown");
            server.shutdown();
            logger.info("Server stopped");
        }));
        logger.info("server waiting for client connections...");
        server.awaitTermination();


    }
}
