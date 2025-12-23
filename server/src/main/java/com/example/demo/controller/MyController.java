package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MyService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class MyController {
    @Autowired
    MyService service;

    @GetMapping
    Mono<String> getServiceLastPing(@RequestParam(name = "service") String serviceName) {
        return service.getLastPingOfServer(serviceName);
    }
}
