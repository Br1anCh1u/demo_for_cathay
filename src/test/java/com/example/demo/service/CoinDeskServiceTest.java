package com.example.demo.service;


import com.example.demo.feign.client.coindesk.CoinDeskClient;
import com.example.demo.jpa.entity.MyCurrency;
import com.example.demo.pojo.CoinDeskResponse;
import com.example.demo.pojo.CoinDeskResponseCurrency;
import com.example.demo.pojo.client.CoinDesk;
import com.example.demo.service.mycurrency.MyCurrencyService;
import com.example.demo.service.coindesk.CoinDeskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CoinDeskServiceTest {

    @Mock
    private MyCurrencyService myCurrencyService;

    @Mock
    private CoinDeskClient coinDeskClient;

    @InjectMocks
    private CoinDeskService coinDeskService;

    @Test
    public void testGetCoinDesk() {
        // mock CoinDesk API 回傳資料
        CoinDesk coinDesk = new CoinDesk();
        CoinDesk.CoinDeskTime time = new CoinDesk.CoinDeskTime();
        time.setUpdatedISO("2023-10-10T08:00:00+00:00");
        coinDesk.setTime(time);
        coinDesk.setDisclaimer("Test Disclaimer");
        coinDesk.setChartName("Bitcoin");

        CoinDesk.CoinDeskCurrency usdBpi = new CoinDesk.CoinDeskCurrency();
        usdBpi.setCode("USD");
        usdBpi.setSymbol("&#36;");
        usdBpi.setRate("34,000.0");
        usdBpi.setRateFloat(34000.0f);
        usdBpi.setDescription("United States Dollar");

        Map<String, CoinDesk.CoinDeskCurrency> bpiMap = new HashMap<>();
        bpiMap.put("USD", usdBpi);
        coinDesk.setBpi(bpiMap);

        when(coinDeskClient.getCoinDeskJson()).thenReturn(coinDesk);

        CoinDesk response = coinDeskService.getCoinDesk();
        verify(coinDeskClient, atMostOnce()).getCoinDeskJson();
        assertEquals("Test Disclaimer", response.getDisclaimer());
        assertEquals("Bitcoin", response.getChartName());
        assertTrue(response.getBpi().containsKey("USD"));

        CoinDesk.CoinDeskCurrency currency = response.getBpi().get("USD");
        assertEquals("USD", currency.getCode());
        assertEquals("&#36;", currency.getSymbol());
        assertEquals("34,000.0", currency.getRate());
        assertEquals(34000.0f, currency.getRateFloat());
        assertEquals("United States Dollar", currency.getDescription());

    }

    @Test
    public void testGetConvertedCoinDesk() {
        // mock CoinDesk API 回傳資料
        CoinDesk coinDesk = new CoinDesk();
        CoinDesk.CoinDeskTime time = new CoinDesk.CoinDeskTime();
        time.setUpdatedISO("2023-10-10T08:00:00+00:00");
        coinDesk.setTime(time);
        coinDesk.setDisclaimer("Test Disclaimer");
        coinDesk.setChartName("Bitcoin");

        CoinDesk.CoinDeskCurrency usdBpi = new CoinDesk.CoinDeskCurrency();
        usdBpi.setCode("USD");
        usdBpi.setSymbol("&#36;");
        usdBpi.setRate("34,000.0");
        usdBpi.setRateFloat(34000.0f);
        usdBpi.setDescription("United States Dollar");

        CoinDesk.CoinDeskCurrency usdBpi2 = new CoinDesk.CoinDeskCurrency();
        usdBpi2.setCode("TWD");
        usdBpi2.setDescription("New Taiwan dollar");

        Map<String, CoinDesk.CoinDeskCurrency> bpiMap = new HashMap<>();
        bpiMap.put("USD", usdBpi);
        bpiMap.put("TWD", usdBpi2);
        coinDesk.setBpi(bpiMap);

        when(coinDeskClient.getCoinDeskJson()).thenReturn(coinDesk);

        // mock db資料
        MyCurrency myCurrency = new MyCurrency();
        myCurrency.setCurrencyCode("USD");
        myCurrency.setCurrencyNumber("840");
        myCurrency.setCurrencyCht("美金");

        when(myCurrencyService.getCurrency(null, "USD")).thenReturn(Collections.singletonList(myCurrency));
        when(myCurrencyService.getCurrency(null, "TWD")).thenReturn(new ArrayList<>());

        // Act
        CoinDeskResponse response = coinDeskService.getConvertedCoinDesk();

        // Assert
        assertEquals("Test Disclaimer", response.getDisclaimer());
        assertEquals("Bitcoin", response.getChartName());
        assertNotNull(response.getUpdatedAt());
        assertTrue(response.getBpi().containsKey("USD"));

        CoinDeskResponseCurrency usdResponse = response.getBpi().get("USD");

        assertEquals("USD", usdResponse.getCode());
        assertEquals("840", usdResponse.getNumber());
        assertEquals("&#36;", usdResponse.getSymbol());
        assertEquals("34,000.0", usdResponse.getRate());
        assertEquals("United States Dollar", usdResponse.getDescription());
        assertEquals("美金", usdResponse.getDescriptionCht());
        assertEquals(34000.0f, usdResponse.getRateFloat());
    }
}
