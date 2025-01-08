package com.example.configuration;

import com.example.service.grpc.CourseGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {
    @Value("${grpc.port}")
    private int grpcPort;

    @Bean
    public Server grpcServer(CourseGrpcService courseGrpcService) {
        return ServerBuilder.forPort(grpcPort)
                .addService(courseGrpcService)
                .build();
    }
}
