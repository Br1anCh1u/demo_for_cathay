package com.example.demo.controller;

import com.example.demo.pojo.ClientTrackResponse;
import com.example.demo.pojo.CoinDeskResponse;
import com.example.demo.pojo.client.CoinDesk;
import com.example.demo.service.coindesk.CoinDeskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.slf4j.MDC;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoinDeskControllerTest {

    @Mock
    private CoinDeskService coinDeskService;

    @InjectMocks
    private CoinDeskController coinDeskController;

    @BeforeEach
    void setUp() {
        MDC.put("traceId", "test-trace-id");
    }

    @Test
    public void testGetCoinDesk() {
        CoinDesk mockData = new CoinDesk();
        mockData.setDisclaimer("Test Disclaimer");

        when(coinDeskService.getCoinDesk()).thenReturn(mockData);

        CoinDesk result = coinDeskController.getCoinDesk();

        assertNotNull(result);
        assertEquals("Test Disclaimer", result.getDisclaimer());
        verify(coinDeskService).getCoinDesk();
    }

    @Test
    public void testGetConvertedCoinDesk() {
        CoinDeskResponse mockResponse = new CoinDeskResponse();
        mockResponse.setDisclaimer("Converted Test");

        when(coinDeskService.getConvertedCoinDesk()).thenReturn(mockResponse);

        ClientTrackResponse<CoinDeskResponse> result = coinDeskController.getConvertedCoinDesk();

        assertNotNull(result);
        assertEquals("test-trace-id", result.getTraceId());
        assertEquals("Converted Test", result.getResult().getDisclaimer());
        verify(coinDeskService).getConvertedCoinDesk();
    }
}
