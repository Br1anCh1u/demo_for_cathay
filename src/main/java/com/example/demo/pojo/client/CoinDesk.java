package com.example.demo.pojo.client;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class CoinDesk {

    private CoinDeskTime time;
    private String disclaimer;
    private String chartName;
    private Map<String, CoinDeskCurrency> bpi;


    @Data
    public static class CoinDeskTime{
        private String updated;
        private String updatedISO;
        @JsonProperty("updateduk")
        private String updatedUK;
    }

    @Data
    public static class CoinDeskCurrency {
        private String code;
        private String symbol;
        private String rate;
        private String description;

        @JsonProperty("rate_float")
        private double rateFloat;
    }
}
