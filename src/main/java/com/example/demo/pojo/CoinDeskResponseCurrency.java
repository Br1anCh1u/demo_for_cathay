package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinDeskResponseCurrency {
    private String code;
    private String number;
    private String symbol;
    private String rate;
    private String description;
    private String descriptionCht;
    private double rateFloat;
}
