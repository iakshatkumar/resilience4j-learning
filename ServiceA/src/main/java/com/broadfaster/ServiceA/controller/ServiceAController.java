package com.broadfaster.ServiceA.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/a")
public class ServiceAController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8082/";

    private static final String SERVICE_A  =  "serviceA";

    private int retryCount = 1;

    @GetMapping
//    @CircuitBreaker(name = SERVICE_A, fallbackMethod = "serviceAFallback")
//    @Retry(name = SERVICE_A)
//    we can also provide fallback method to retry as well
    @RateLimiter(name = SERVICE_A)
    public String serviceA(){

        String url = BASE_URL + "b";
        System.out.println("Retry Mechanism kicks in  "  +  retryCount++ + " times");
        return restTemplate.getForObject(url , String.class);
    }

    public String serviceAFallback(Exception e){
        return "this is a fallback method of service A";
    }

}
