package com.example.demo.controller;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.BusinessNotFoundException;
import com.example.demo.util.ClientTrackDataUtil;
import com.example.demo.jpa.entity.MyCurrency;
import com.example.demo.jpa.request.MyCurrencyRequest;
import com.example.demo.pojo.ClientTrackResponse;
import com.example.demo.service.mycurrency.MyCurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class MyCurrencyController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MyCurrencyService myCurrencyService;

    @GetMapping
    public ClientTrackResponse<List<MyCurrency>> getCurrency(@RequestParam @Nullable String currencyNumber, @RequestParam @Nullable String currencyCode) throws BusinessException {
        List<MyCurrency> resultList = myCurrencyService.getCurrency(currencyNumber, currencyCode);
        ClientTrackResponse<List<MyCurrency>> result = new ClientTrackResponse<>(MDC.get("traceId"), ClientTrackDataUtil.getTrace(), resultList);
        ClientTrackDataUtil.clear();

        log.info("response......" + result);
        return result;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCurrency(@RequestBody @Nullable MyCurrencyRequest request) throws BusinessException {
        myCurrencyService.insertCurrency(request);
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateCurrency(@RequestParam @Nullable String currencyNumber, @RequestParam @Nullable String currencyCode, @RequestBody MyCurrencyRequest request) throws BusinessException, BusinessNotFoundException {
        myCurrencyService.updateCurrency(currencyNumber, currencyCode, request);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void deleteCurrency(@RequestParam @Nullable String currencyNumber, @RequestParam @Nullable String currencyCode) throws BusinessException, BusinessNotFoundException {
        myCurrencyService.deleteCurrency(currencyNumber, currencyCode);
    }

}
