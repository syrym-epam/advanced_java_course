package com.grpc.demo.grpc;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class ProductGrpcServer {
    private static final Logger logger = Logger.getLogger(ProductGrpcServer.class.getName());

    private Server server;

    private void start() throws IOException {
        // Port 8080 as requested
        int port = 8080;
        server = ServerBuilder.forPort(port)
                .addService(new ProductInfoServiceImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("*** shutting down gRPC server since JVM is shutting down");
            try {
                ProductGrpcServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            logger.info("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final ProductGrpcServer server = new ProductGrpcServer();
        server.start();
        server.blockUntilShutdown();
    }
 
    static class ProductInfoServiceImpl extends ProductInfoServiceGrpc.ProductInfoServiceImplBase {
        @Override
        public void getProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
            long requestedId = request.getId();
            logger.info("Received request for product ID: " + requestedId);

            // Mock database or service fetch using your entity/DTO concept
            ProductResponse response = ProductResponse.newBuilder()
                    .setId(requestedId)
                    .setName("Sample Product " + requestedId)
                    .setPrice(new BigDecimal("99.99").toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}