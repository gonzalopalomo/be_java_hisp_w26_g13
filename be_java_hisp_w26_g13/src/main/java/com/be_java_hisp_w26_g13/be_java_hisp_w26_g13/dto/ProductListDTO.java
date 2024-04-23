package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductListDTO {
    @JsonProperty("user_id")
    private int userId;
    private List<ProductDTO> products;
}
