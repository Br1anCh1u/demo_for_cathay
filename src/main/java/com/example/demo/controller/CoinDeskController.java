package com.example.demo.controller;

import com.example.demo.pojo.client.CoinDesk;
import com.example.demo.util.ClientTrackDataUtil;
import com.example.demo.pojo.ClientTrackResponse;
import com.example.demo.pojo.CoinDeskResponse;
import com.example.demo.service.coindesk.CoinDeskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coinDesk")
public class CoinDeskController {

    private final Logger log = LoggerFactory.getLogger(CoinDeskController.class);

    @Autowired
    private CoinDeskService coinDeskService;

    @GetMapping()
    public CoinDesk getCoinDesk(){
        CoinDesk response = coinDeskService.getCoinDesk();
        log.info("response......"+response);
        return response;
    }


    @GetMapping("/converted")
    public ClientTrackResponse<CoinDeskResponse> getConvertedCoinDesk(){
        CoinDeskResponse response = coinDeskService.getConvertedCoinDesk();
        ClientTrackResponse<CoinDeskResponse> result = new ClientTrackResponse<>(MDC.get("traceId"), ClientTrackDataUtil.getTrace(), response);
        ClientTrackDataUtil.clear();
        log.info("response......"+result);
        return result;
    }
}
