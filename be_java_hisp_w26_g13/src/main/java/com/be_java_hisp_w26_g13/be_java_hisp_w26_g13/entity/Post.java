package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {
    private int userId;
    private LocalDate date;
    private Product product;
    private int category;
    private Double price;
}
