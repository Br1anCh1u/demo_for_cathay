package com.example.demo.service.mycurrency;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.BusinessNotFoundException;
import com.example.demo.jpa.entity.MyCurrency;
import com.example.demo.jpa.repository.MyCurrencyRepository;
import com.example.demo.jpa.request.MyCurrencyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MyCurrencyService implements MyCurrencyInterface {

    @Autowired
    private MyCurrencyRepository myCurrencyRepository;

    @Override
    public List<MyCurrency> getCurrency(@Nullable String currencyNumber, @Nullable String currencyCode) {
        if(!StringUtils.hasText(currencyCode) && !StringUtils.hasText(currencyNumber)) {
            return myCurrencyRepository.findAll();
        } else if(StringUtils.hasText(currencyCode)) {
            return myCurrencyRepository.findByCurrencyCode(currencyCode.toUpperCase());
        } else {
            return myCurrencyRepository.findByCurrencyNumber(currencyNumber);
        }
    }

    @Override
    public void insertCurrency(MyCurrencyRequest requestBody) {
        if(requestBody == null) {
            throw new BusinessException("E0001", "request body");
        }
        if(!StringUtils.hasText(requestBody.getCurrencyCode())
                || !StringUtils.hasText(requestBody.getCurrencyNumber())
                || !StringUtils.hasText(requestBody.getCurrencyCht())) {
            throw new BusinessException("E0002");
        }

        if(myCurrencyRepository.existsByCurrencyCode(requestBody.getCurrencyCode().toUpperCase())
                || myCurrencyRepository.existsByCurrencyCode(requestBody.getCurrencyCode().toLowerCase())) {
            throw new BusinessException("E0003", requestBody.getCurrencyCode());
        }

        if(myCurrencyRepository.existsByCurrencyNumber(requestBody.getCurrencyNumber())) {
            throw new BusinessException("E0003", requestBody.getCurrencyNumber());
        }

        MyCurrency currency = new MyCurrency();
        currency.setCurrencyCode(requestBody.getCurrencyCode().toUpperCase());
        currency.setCurrencyNumber(requestBody.getCurrencyNumber());
        currency.setCurrencyCht(requestBody.getCurrencyCht());

        myCurrencyRepository.save(currency);
    }

    @Override
    public void updateCurrency(String currencyNumber, String currencyCode, MyCurrencyRequest requestBody) throws BusinessException, BusinessNotFoundException {
        if(StringUtils.hasText(currencyNumber) && StringUtils.hasText(currencyCode)) {
            throw new BusinessException("E0004", "currencyNumber, currencyCode");
        }
        if(!StringUtils.hasText(currencyNumber) && !StringUtils.hasText(currencyCode)) {
            throw new BusinessException("E0005", "currencyNumber, currencyCode");
        }
        if(StringUtils.hasText(currencyNumber)) {
            updateCurrencyByCurrencyNumber(currencyNumber, requestBody);
        }
        if(StringUtils.hasText(currencyCode)) {
            updateCurrencyByCurrencyCode(currencyCode, requestBody);
        }
    }

    @Override
    public void updateCurrencyByCurrencyNumber(String currencyNumber, MyCurrencyRequest requestBody) throws BusinessException, BusinessNotFoundException {
        if(requestBody == null) {
            throw new BusinessException("E0001");
        }
        if(!myCurrencyRepository.existsByCurrencyNumber(currencyNumber)) {
            throw new BusinessNotFoundException();
        }

        MyCurrency currency = myCurrencyRepository.findByCurrencyNumber(currencyNumber).get(0);
        if(StringUtils.hasText(requestBody.getCurrencyCode())) {
            currency.setCurrencyCode(requestBody.getCurrencyCode());
        }
        if(StringUtils.hasText(requestBody.getCurrencyCht())) {
            currency.setCurrencyCht(requestBody.getCurrencyCht());
        }
        myCurrencyRepository.save(currency);
    }

    @Override
    public void updateCurrencyByCurrencyCode(String currencyCode, MyCurrencyRequest requestBody) throws BusinessException, BusinessNotFoundException {
        if(requestBody == null) {
            throw new BusinessException("E0001");
        }
        if(!myCurrencyRepository.existsByCurrencyCode(currencyCode)) {
            throw new BusinessNotFoundException();
        }

        MyCurrency currency = myCurrencyRepository.findByCurrencyCode(currencyCode).get(0);
        if(StringUtils.hasText(requestBody.getCurrencyNumber())) {
            currency.setCurrencyNumber(requestBody.getCurrencyNumber());
        }
        if(StringUtils.hasText(requestBody.getCurrencyCht())) {
            currency.setCurrencyCht(requestBody.getCurrencyCht());
        }
        myCurrencyRepository.save(currency);
    }

    @Override
    public void deleteCurrency(String currencyNumber, String currencyCode) {
        if(!StringUtils.hasText(currencyNumber) && !StringUtils.hasText(currencyCode)) {
            throw new BusinessException("E0005", "currencyNumber, currencyCode");
        }
        deleteCurrencyByCurrencyNumber(currencyNumber);
        deleteCurrencyByCurrencyCode(currencyCode);

    }

    @Override
    public void deleteCurrencyByCurrencyNumber(String currencyNumber) {
        myCurrencyRepository.removeAllByCurrencyNumber(currencyNumber);
    }

    @Override
    public void deleteCurrencyByCurrencyCode(String currencyCode) {
        myCurrencyRepository.removeAllByCurrencyCode(currencyCode);
    }


}
