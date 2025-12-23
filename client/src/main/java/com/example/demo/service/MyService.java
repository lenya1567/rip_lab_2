package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.client.ReactiveClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MyService {
    private final ReactiveClient client;

    public MyService(ReactiveClient client) {
        this.client = client;
    }

    public Mono<String> getData(String serviceName) {
        return client.fetchData(serviceName)
                .map(incomingData -> "Procesed: " + incomingData);
    }

    public Flux<String> getGroupData(String serviceName) {
        return client.fetchGroup(serviceName);
    }
}
