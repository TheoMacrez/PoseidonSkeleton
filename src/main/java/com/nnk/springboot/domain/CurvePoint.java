package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "curvepoint")
@Data
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer curveId;
    private Timestamp asOfDate;

    @Digits(integer=10, fraction=2)
    private Double term;

    @Digits(integer=10, fraction=2)
    private Double value;

    private Timestamp creationDate;

}
