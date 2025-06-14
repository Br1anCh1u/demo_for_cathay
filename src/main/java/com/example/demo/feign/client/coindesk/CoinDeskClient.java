package com.example.demo.feign.client.coindesk;

import com.example.demo.pojo.client.CoinDesk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "coinDesk"
        , contextId = "coinDesk"
        , url = "${coinDesk.api.url}"
        , configuration = CoinDeskFeignConfig.class)
public interface CoinDeskClient {

    @GetMapping("/coindesk.json")
    CoinDesk getCoinDeskJson();
}
