package com.example.demo.jpa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCurrencyRequest {
    @Nullable
    private String currencyNumber;
    @Nullable
    private String currencyCode;
    @Nullable
    private String currencyCht;
}
