package com.grpc.demo.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;  
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductGrpcClient {
    private static final Logger logger = Logger.getLogger(ProductGrpcClient.class.getName());

    public static void main(String[] args) {
        String target = "localhost:8080";
        
        // Create a communication channel to the server on port 8080
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext() // Disables TLS for local testing
                .build();

        try {
            ProductInfoServiceGrpc.ProductInfoServiceBlockingStub blockingStub = 
                    ProductInfoServiceGrpc.newBlockingStub(channel);

            long productId = 42L;
            ProductRequest request = ProductRequest.newBuilder().setId(productId).build();
            
            logger.info("Sending request for Product ID: " + productId);
            ProductResponse response = blockingStub.getProduct(request);
            
            System.out.println("--- Received Response from gRPC Server ---");
            System.out.println("Product ID   : " + response.getId());
            System.out.println("Product Name : " + response.getName());
            System.out.println("Product Price: " + response.getPrice());

        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        } finally {
            try {
                channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}