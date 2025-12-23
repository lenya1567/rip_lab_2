package com.example.demo.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
@Order(1)
public class LoggerFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.printf(">>> \u001B[34mIncoming request: \u001B[0m\u001B[35m%s\u001B[0m\n",
                exchange.getRequest().getURI());
        return chain.filter(exchange)
                .doOnTerminate(
                        () -> {
                            System.out.printf("<<< \u001B[34mRequest completed: \u001B[0m");
                            if (exchange.getResponse().getStatusCode().is1xxInformational()) {
                                System.out.println(exchange.getResponse().getStatusCode().toString());
                            } else if (exchange.getResponse().getStatusCode().is2xxSuccessful()) {
                                System.out.println("\u001B[32m" + exchange.getResponse().getStatusCode().toString()
                                        + "\u001B[0m");
                            } else if (exchange.getResponse().getStatusCode().is3xxRedirection()) {
                                System.out.println("\u001B[33m" + exchange.getResponse().getStatusCode().toString()
                                        + "\u001B[0m");
                            } else if (exchange.getResponse().getStatusCode().is4xxClientError()) {
                                System.out.println("\u001B[31m" + exchange.getResponse().getStatusCode().toString()
                                        + "\u001B[0m");
                            } else if (exchange.getResponse().getStatusCode().is5xxServerError()) {
                                System.out.println("\u001B[31m" + exchange.getResponse().getStatusCode().toString()
                                        + "\u001B[0m");
                            }
                            System.out.println();
                        });

    }
}