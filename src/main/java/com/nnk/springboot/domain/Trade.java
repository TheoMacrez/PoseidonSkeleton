package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "trade")
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @Digits(integer=10, fraction=2, message = "Buy Quantity must be a valid number")
    private BigDecimal buyQuantity;

    @Digits(integer=10, fraction=2, message = "Sell Quantity must be a valid number")
    private BigDecimal sellQuantity;

    @Digits(integer=10, fraction=2, message = "Buy Price must be a valid number")
    private BigDecimal buyPrice;

    @Digits(integer=10, fraction=2, message = "Sell Price must be a valid number")
    private BigDecimal sellPrice;

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
