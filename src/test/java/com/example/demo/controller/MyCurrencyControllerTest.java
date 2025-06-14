package com.example.demo.controller;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.BusinessNotFoundException;

import com.example.demo.jpa.entity.MyCurrency;
import com.example.demo.jpa.request.MyCurrencyRequest;
import com.example.demo.pojo.ClientTrackResponse;
import com.example.demo.service.mycurrency.MyCurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.slf4j.MDC;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyCurrencyControllerTest {

    @Mock
    private MyCurrencyService myCurrencyService;

    @InjectMocks
    private MyCurrencyController myCurrencyController;

    @BeforeEach
    void setup() {
        MDC.put("traceId", "test-trace-id");
    }

    @Test
    public void testGetCurrency() throws BusinessException {
        MyCurrency currency = new MyCurrency();
        currency.setCurrencyCode("USD");
        currency.setCurrencyNumber("840");

        when(myCurrencyService.getCurrency("840", "USD")).thenReturn(Collections.singletonList(currency));

        ClientTrackResponse<List<MyCurrency>> response = myCurrencyController.getCurrency("840", "USD");

        assertNotNull(response);
        assertEquals("test-trace-id", response.getTraceId());
        assertEquals(1, response.getResult().size());
        assertEquals("USD", response.getResult().get(0).getCurrencyCode());

        verify(myCurrencyService).getCurrency("840", "USD");
    }

    @Test
    public void testInsertCurrency() throws BusinessException {
        MyCurrencyRequest request = new MyCurrencyRequest();
        request.setCurrencyCode("USD");
        request.setCurrencyNumber("840");

        doNothing().when(myCurrencyService).insertCurrency(request);

        myCurrencyController.insertCurrency(request);

        verify(myCurrencyService).insertCurrency(request);
    }

    @Test
    public void testUpdateCurrency() throws BusinessException, BusinessNotFoundException {
        MyCurrencyRequest request = new MyCurrencyRequest();
        request.setCurrencyCode("USD");

        doNothing().when(myCurrencyService).updateCurrency("840", "USD", request);

        myCurrencyController.updateCurrency("840", "USD", request);

        verify(myCurrencyService).updateCurrency("840", "USD", request);
    }

    @Test
    public void testDeleteCurrency() throws BusinessException, BusinessNotFoundException {
        doNothing().when(myCurrencyService).deleteCurrency("840", "USD");

        myCurrencyController.deleteCurrency("840", "USD");

        verify(myCurrencyService).deleteCurrency("840", "USD");
    }
}
