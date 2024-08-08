package com.myshop.myshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    private Long id;

    private String image;

    private String name;

    private Double costPriceHeader;

    private Double wholesalePrice;

    private Double retailPrice;

    private Integer stock;

  

    // Getters and Setters

   
}
