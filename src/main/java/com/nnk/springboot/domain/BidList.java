package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@Data
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotNull(message = "Bid Quantity is mandatory")
    @Digits(integer = 10, fraction = 2, message = "Bid Quantity must be a valid number")
    private Double bidQuantity;

    @Digits(integer = 10, fraction = 2, message = "Ask Quantity must be a valid number")
    private Double askQuantity;

    @Digits(integer = 10, fraction = 2, message = "Bid must be a valid number")
    private Double bid;

    @Digits(integer = 10, fraction = 2, message = "Ask must be a valid number")
    private Double ask;

    private String benchmark;

    private Timestamp bidListDate;

    private String commentary;

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
