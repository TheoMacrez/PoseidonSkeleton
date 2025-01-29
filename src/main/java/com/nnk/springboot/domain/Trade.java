package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "trade")
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;


    private String account;


    private String type;


    @Digits(integer=10, fraction=2)
    private Double buyQuantity;


    @Digits(integer=10, fraction=2)
    private Double sellQuantity;


    @Digits(integer=10, fraction=2)
    private Double buyPrice;


    @Digits(integer=10, fraction=2)
    private Double sellPrice;


    private String benchmark;


    private Timestamp tradeDate;


    private String security;


    private String status;


    private String trader;


    private String book;


    private String creationName;


    private Timestamp creationDate;


    private String revisionName;


    private Timestamp revisionDate;


    private String dealName;


    private String dealType;


    private String sourceListId;


    private String side;

}
