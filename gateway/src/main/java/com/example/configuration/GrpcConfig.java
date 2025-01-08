package com.example.configuration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.service.grpc.CourseServiceGrpc;

@Configuration
public class GrpcConfig {

    @Value("${grpc.host}")
    private String domainServiceHost;

    @Value("${grpc.port}")
    private int domainServicePort;

    @Bean
    public CourseServiceGrpc.CourseServiceBlockingStub courseServiceStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(domainServiceHost, domainServicePort)
                .usePlaintext()
                .build();
        return CourseServiceGrpc.newBlockingStub(channel);
    }
}

