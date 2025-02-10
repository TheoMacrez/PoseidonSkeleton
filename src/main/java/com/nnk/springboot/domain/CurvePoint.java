package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "curvepoint")
@Data
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Curve ID is mandatory")
    private Integer curveId;

    private Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    @Digits(integer = 10, fraction = 2, message = "Term must be a valid number")
    private Double term;

    @NotNull(message = "Value is mandatory")
    @Digits(integer = 10, fraction = 2, message = "Value must be a valid number")
    private Double value;

    private Timestamp creationDate;
}
