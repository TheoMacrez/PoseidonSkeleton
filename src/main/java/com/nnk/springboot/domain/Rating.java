package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Moodys Rating is mandatory")
    private String moodysRating;

    @NotBlank(message = "SandP Rating is mandatory")
    private String sandPRating;

    @NotBlank(message = "Fitch Rating is mandatory")
    private String fitchRating;

    @NotNull(message = "Order Number is mandatory")
    private Integer orderNumber;
}
