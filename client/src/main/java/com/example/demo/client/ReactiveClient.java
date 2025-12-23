package com.example.demo.client;

import java.time.Duration;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
public class ReactiveClient {
    private final WebClient webClient;

    public ReactiveClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8083")
                .filter(logRequest())
                .build();
    }

    public Flux<String> fetchGroup(String serviceName) {
        return Flux.range(0, 200).flatMap(item -> fetchData(serviceName + item));
    }

    public Mono<String> fetchData(String serviceName) {
        return webClient.get()
                .uri(urlBuilder -> urlBuilder.path("/").queryParam("service", serviceName).build())
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
                .onErrorReturn("Service error!");
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.printf("=== \u001B[36mRequest to :8083: \u001B[0m\u001B[35m%s\u001B[0m\n", clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
}
