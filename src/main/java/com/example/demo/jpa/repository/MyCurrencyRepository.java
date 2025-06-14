package com.example.demo.jpa.repository;

import com.example.demo.jpa.entity.MyCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MyCurrencyRepository extends JpaRepository<MyCurrency, Integer> {
    List<MyCurrency> findByCurrencyCode(String currencyCode);
    List<MyCurrency> findByCurrencyNumber(String currencyNumber);
    boolean existsByCurrencyCode(String currencyCode);
    boolean existsByCurrencyNumber(String currencyNumber);
    void removeAllByCurrencyCode(String currencyCode);
    void removeAllByCurrencyNumber(String currencyNumber);
}
