package com.example.configuration;

import io.grpc.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcServerStarter implements CommandLineRunner {
    private final Server grpcServer;

    @Override
    public void run(String... args) throws Exception {
        grpcServer.start();
        grpcServer.awaitTermination();
    }
}
