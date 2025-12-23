package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.ReactiveClient;
import com.example.demo.service.MyService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class MyController {

    private final MyService service;

    public MyController() {
        service = new MyService(new ReactiveClient());
    }

    @GetMapping(value = "/status", produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> getServiceStatus(@RequestParam(name = "service") String serviceName) {
        return service.getData(serviceName);
    }

    @GetMapping(value = "/report", produces = org.springframework.http.MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> getServiceGroupReport(@RequestParam(name = "service") String serviceName) {
        return service.getGroupData(serviceName);
    }
}
