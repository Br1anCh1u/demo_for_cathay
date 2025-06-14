package com.example.demo.feign.client.coindesk;

import com.example.demo.feign.FeignTraceClient;
import feign.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoinDeskFeignConfig {

    @Bean
    public Client feignClient() {
        return new FeignTraceClient();
    }

}