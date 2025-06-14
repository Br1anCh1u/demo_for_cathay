package com.example.demo.service.coindesk;

import com.example.demo.feign.client.coindesk.CoinDeskClient;
import com.example.demo.jpa.entity.MyCurrency;
import com.example.demo.pojo.CoinDeskResponse;
import com.example.demo.pojo.CoinDeskResponseCurrency;
import com.example.demo.pojo.client.CoinDesk;
import com.example.demo.service.mycurrency.MyCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoinDeskService {

    @Autowired
    MyCurrencyService myCurrencyService;

    @Autowired
    CoinDeskClient coinDeskClient;

    public CoinDesk getCoinDesk(){
        return coinDeskClient.getCoinDeskJson();
    }

    public CoinDeskResponse getConvertedCoinDesk() {
        // fetch data
        CoinDesk coinDesk = coinDeskClient.getCoinDeskJson();

        // 轉時間
        // 取ISO時間來轉
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(coinDesk.getTime().getUpdatedISO());
        // 轉成系統時間
        LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
        String formattedDateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        // 轉bpi data
        Map<String, CoinDeskResponseCurrency> map = new HashMap<>();
        coinDesk.getBpi().forEach((k, v) -> {
            List<MyCurrency> myCurrencyList = myCurrencyService.getCurrency(null, k);
            if(!myCurrencyList.isEmpty()) {
                MyCurrency myCurrency = myCurrencyList.stream().findFirst().get();
                CoinDeskResponseCurrency coinDeskResponseCurrency = new CoinDeskResponseCurrency();
                coinDeskResponseCurrency.setCode(myCurrency.getCurrencyCode());
                coinDeskResponseCurrency.setNumber(myCurrency.getCurrencyNumber());
                coinDeskResponseCurrency.setSymbol(v.getSymbol());
                coinDeskResponseCurrency.setRate(v.getRate());
                coinDeskResponseCurrency.setDescription(v.getDescription());
                coinDeskResponseCurrency.setDescriptionCht(myCurrency.getCurrencyCht());
                coinDeskResponseCurrency.setRateFloat(v.getRateFloat());
                map.put(k, coinDeskResponseCurrency);
            }
        });

        // convert data
        CoinDeskResponse response = new CoinDeskResponse();
        response.setUpdatedAt(formattedDateTime);
        response.setDisclaimer(coinDesk.getDisclaimer());
        response.setChartName(coinDesk.getChartName());
        response.setBpi(map);
        return response;
    }

}
