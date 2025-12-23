package com.example.demo.filter;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(2)
public class AuthFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        List<HttpCookie> authToken = exchange.getRequest().getCookies().get("SECRET_KEY");
        if (authToken != null && authToken.size() > 0 && authToken.get(0).getValue().equals("SUPER_SECRET_KEY")) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setRawStatusCode(403);
            return exchange.getResponse().setComplete();
        }
    }
}
