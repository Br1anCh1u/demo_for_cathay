package com.example.demo.jpa.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "currency",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "currency_number"),
                @UniqueConstraint(columnNames = "currency_code")
        })
public class MyCurrency {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "currency_number", length = 3, nullable = false)
    private String currencyNumber;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "currency_cht", length = 50, nullable = false)
    private String currencyCht;
}
