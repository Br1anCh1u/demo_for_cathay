package com.example.demo.service.mycurrency;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.BusinessNotFoundException;
import com.example.demo.jpa.entity.MyCurrency;
import com.example.demo.jpa.request.MyCurrencyRequest;
import org.springframework.lang.Nullable;

import java.util.List;

public interface MyCurrencyInterface {

    List<MyCurrency> getCurrency( @Nullable String currencyNumber, @Nullable String currencyCode);

    void insertCurrency(MyCurrencyRequest requestBody);

    void updateCurrency(@Nullable String currencyNumber, @Nullable String currencyCode, MyCurrencyRequest requestBody) throws BusinessException, BusinessNotFoundException;

    void updateCurrencyByCurrencyNumber(String currencyNumber, MyCurrencyRequest requestBody) throws BusinessException, BusinessNotFoundException;

    void updateCurrencyByCurrencyCode(String currencyCode, MyCurrencyRequest requestBody) throws BusinessException, BusinessNotFoundException;

    void deleteCurrency(@Nullable String currencyNumber, @Nullable String currencyCode);

    void deleteCurrencyByCurrencyNumber(String currencyNumber);

    void deleteCurrencyByCurrencyCode(String currencyCode);
}
