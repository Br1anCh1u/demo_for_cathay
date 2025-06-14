package com.example.demo.pojo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class CoinDeskResponse{
    private String updatedAt;
    private String disclaimer;
    private String chartName;
    private Map<String, CoinDeskResponseCurrency> bpi;
}
