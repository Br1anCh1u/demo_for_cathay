package com.example.demo.service;

import com.example.demo.exception.BusinessException;
import com.example.demo.exception.BusinessNotFoundException;
import com.example.demo.jpa.entity.MyCurrency;
import com.example.demo.jpa.repository.MyCurrencyRepository;
import com.example.demo.jpa.request.MyCurrencyRequest;
import com.example.demo.service.mycurrency.MyCurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MyCurrencyServiceTest {

    @Mock
    MyCurrencyRepository repository;

    @InjectMocks
    MyCurrencyService service;

    MyCurrency sampleCurrency;

    @BeforeEach
    void setup() {
        sampleCurrency = new MyCurrency();
        sampleCurrency.setCurrencyCode("USD");
        sampleCurrency.setCurrencyNumber("840");
        sampleCurrency.setCurrencyCht("美金");
    }

    // getCurrency

    @Test
    void testGetCurrency_NoParam_ReturnAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleCurrency));
        List<MyCurrency> result = service.getCurrency(null, null);
        assertEquals(1, result.size());
    }

    @Test
    void testGetCurrency_CodeOnly() {
        when(repository.findByCurrencyCode("USD")).thenReturn(Collections.singletonList(sampleCurrency));
        List<MyCurrency> result = service.getCurrency(null, "usd");
        assertEquals("USD", result.get(0).getCurrencyCode());
    }

    @Test
    void testGetCurrency_NumberOnly() {
        when(repository.findByCurrencyNumber("840")).thenReturn(Collections.singletonList(sampleCurrency));
        List<MyCurrency> result = service.getCurrency("840", "");
        assertEquals("USD", result.get(0).getCurrencyCode());
    }

    // insertCurrency

    @Test
    void testInsertCurrency_NullBody_ThrowsE0001() {
        BusinessException ex = assertThrows(BusinessException.class, () -> service.insertCurrency(null));
        assertEquals("E0001", ex.getErrorCode());
    }

    @Test
    void testInsertCurrency_MissingCurrencyNumber_ThrowsE0002() {
        MyCurrencyRequest req = new MyCurrencyRequest("", "USD", "美金");
        BusinessException ex = assertThrows(BusinessException.class, () -> service.insertCurrency(req));
        assertEquals("E0002", ex.getErrorCode());
    }

    @Test
    void testInsertCurrency_MissingCurrencyCode_ThrowsE0002() {
        MyCurrencyRequest req = new MyCurrencyRequest("840", "", "美金");
        BusinessException ex = assertThrows(BusinessException.class, () -> service.insertCurrency(req));
        assertEquals("E0002", ex.getErrorCode());
    }

    @Test
    void testInsertCurrency_MissingCurrencyCht_ThrowsE0002() {
        MyCurrencyRequest req = new MyCurrencyRequest("840", "USD", "");
        BusinessException ex = assertThrows(BusinessException.class, () -> service.insertCurrency(req));
        assertEquals("E0002", ex.getErrorCode());
    }

    @Test
    void testInsertCurrency_DuplicatedCodeUpperCase_ThrowsE0003() {
        MyCurrencyRequest req = new MyCurrencyRequest("840", "USD", "美金");

        when(repository.existsByCurrencyCode("USD")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.insertCurrency(req));
        assertEquals("E0003", ex.getErrorCode());
    }

    @Test
    void testInsertCurrency_DuplicatedCodeLowerCase_ThrowsE0003() {
        MyCurrencyRequest req = new MyCurrencyRequest("840", "usd", "美金");

        when(repository.existsByCurrencyCode("USD")).thenReturn(false);
        when(repository.existsByCurrencyCode("usd")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.insertCurrency(req));
        assertEquals("E0003", ex.getErrorCode());
    }

    @Test
    void testInsertCurrency_DuplicatedNumber_ThrowsE0003() {
        MyCurrencyRequest req = new MyCurrencyRequest("840", "USD", "美金");

        when(repository.existsByCurrencyCode("USD")).thenReturn(false);
        when(repository.existsByCurrencyCode("usd")).thenReturn(false);
        when(repository.existsByCurrencyNumber("840")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.insertCurrency(req));
        assertEquals("E0003", ex.getErrorCode());
    }

    @Test
    void testInsertCurrency_Success() {
        MyCurrencyRequest req = new MyCurrencyRequest("840", "USD", "美金");

        when(repository.existsByCurrencyCode("USD")).thenReturn(false);
        when(repository.existsByCurrencyCode("usd")).thenReturn(false);
        when(repository.existsByCurrencyNumber("840")).thenReturn(false);

        service.insertCurrency(req);

        verify(repository).save(any(MyCurrency.class));
    }

    // updateCurrency

    @Test
    void testUpdateCurrency_BothParamsProvided_ThrowsE0004() {
        MyCurrencyRequest req = new MyCurrencyRequest("USD", "840", "美金");
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.updateCurrency("840", "USD", req));
        assertEquals("E0004", ex.getErrorCode());
    }

    @Test
    void testUpdateCurrency_NoParamsProvided_ThrowsE0005() {
        MyCurrencyRequest req = new MyCurrencyRequest("USD", "840", "美金");
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.updateCurrency("", "", req));
        assertEquals("E0005", ex.getErrorCode());
    }

    @Test
    void testUpdateCurrency_ByNumber() throws Exception {
        MyCurrencyRequest req = new MyCurrencyRequest("USD", null, "美元");
        when(repository.existsByCurrencyNumber("840")).thenReturn(true);
        when(repository.findByCurrencyNumber("840")).thenReturn(Collections.singletonList(sampleCurrency));

        service.updateCurrency("840", null, req);

        verify(repository).save(sampleCurrency);
    }

    @Test
    void testUpdateCurrency_ByCode() throws Exception {
        MyCurrencyRequest req = new MyCurrencyRequest(null, "840", "美金");
        when(repository.existsByCurrencyCode("USD")).thenReturn(true);
        when(repository.findByCurrencyCode("USD")).thenReturn(Collections.singletonList(sampleCurrency));

        service.updateCurrency(null, "USD", req);

        verify(repository).save(sampleCurrency);
    }

    // update by number

    @Test
    void testUpdateCurrencyByNumber_NullRequest_ThrowsE0001() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.updateCurrencyByCurrencyNumber("840", null));
        assertEquals("E0001", ex.getErrorCode());
    }

    @Test
    void testUpdateCurrencyByNumber_NotFound_ThrowsBusinessNotFound() {
        when(repository.existsByCurrencyNumber("840")).thenReturn(false);
        assertThrows(BusinessNotFoundException.class,
                () -> service.updateCurrencyByCurrencyNumber("840", new MyCurrencyRequest()));
    }

    @Test
    void testUpdateCurrencyByNumber_UpdateFields() throws Exception {
        MyCurrencyRequest req = new MyCurrencyRequest(null, "USD1", "美金1");

        MyCurrency mockCurrency = new MyCurrency();
        mockCurrency.setCurrencyCode("USD");
        mockCurrency.setCurrencyNumber("840");
        mockCurrency.setCurrencyCht("美金");

        when(repository.existsByCurrencyNumber("840")).thenReturn(true);
        when(repository.findByCurrencyNumber("840")).thenReturn(Collections.singletonList(mockCurrency));

        service.updateCurrencyByCurrencyNumber("840", req);
        assertEquals("USD1", mockCurrency.getCurrencyCode());
        assertEquals("美金1", mockCurrency.getCurrencyCht());
    }

    // update by code

    @Test
    void testUpdateCurrencyByCode_NullRequest_ThrowsE0001() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.updateCurrencyByCurrencyCode("USD", null));
        assertEquals("E0001", ex.getErrorCode());
    }

    @Test
    void testUpdateCurrencyByCode_NotFound_ThrowsBusinessNotFound() {
        when(repository.existsByCurrencyCode("USD")).thenReturn(false);
        assertThrows(BusinessNotFoundException.class,
                () -> service.updateCurrencyByCurrencyCode("USD", new MyCurrencyRequest()));
    }

    @Test
    void testUpdateCurrencyByCode_UpdateFields() throws Exception {
        MyCurrencyRequest req = new MyCurrencyRequest("840-", null, "美金1");

        MyCurrency mockCurrency = new MyCurrency();
        mockCurrency.setCurrencyCode("USD");
        mockCurrency.setCurrencyNumber("840");
        mockCurrency.setCurrencyCht("美金");

        when(repository.existsByCurrencyCode("USD")).thenReturn(true);
        when(repository.findByCurrencyCode("USD")).thenReturn(Collections.singletonList(mockCurrency));

        service.updateCurrencyByCurrencyCode("USD", req);
        assertEquals("840-", mockCurrency.getCurrencyNumber());
        assertEquals("美金1", mockCurrency.getCurrencyCht());
    }

    // deleteCurrency

    @Test
    void testDeleteCurrency_NoParams_ThrowsE0005() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.deleteCurrency("", ""));
        assertEquals("E0005", ex.getErrorCode());
    }

    @Test
    void testDeleteCurrency_WithParams() {
        service.deleteCurrency("840", "USD");
        verify(repository).removeAllByCurrencyNumber("840");
        verify(repository).removeAllByCurrencyCode("USD");
    }
}
