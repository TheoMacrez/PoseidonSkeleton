package com.nnk.springboot.domain;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Required;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
}
