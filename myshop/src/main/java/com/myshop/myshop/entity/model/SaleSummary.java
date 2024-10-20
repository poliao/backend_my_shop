package com.myshop.myshop.entity.model;

public interface SaleSummary {
    Long getProductId();
    String getProductName();
    Double getCostPriceHeader();
    Double getRetailPrice();
    Integer getTotalQuantity();
    Double getTotalProfit();
   
}