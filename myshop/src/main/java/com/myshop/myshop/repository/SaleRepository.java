package com.myshop.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myshop.myshop.entity.Sale;
import com.myshop.myshop.entity.model.*;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT p.id AS productId, p.name AS productName, p.stock AS productStock, " +
            "p.cost_price_header AS costPriceHeader, p.retail_price AS retailPrice, " +
            "SUM(s.quantity) AS totalQuantity, " +
            "SUM(s.sale_price) AS totalSalePrice, " +
            "SUM(s.cost_price_header) AS totalCostPriceHeader " +
            "FROM product p " +
            "JOIN sale s ON p.id = s.productID " +
            "WHERE EXTRACT(MONTH FROM s.sale_date) = :month AND EXTRACT(YEAR FROM s.sale_date) = :year " +
            "GROUP BY p.id, p.name, p.stock, p.cost_price_header, p.retail_price", nativeQuery = true)
    List<SaleSummary> findSaleSummaryByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query(value = "SELECT p.id AS productId, p.name AS productName, p.stock AS productStock, " +
            "p.cost_price_header AS costPriceHeader, p.retail_price AS retailPrice, " +
            "SUM(s.quantity) AS totalQuantity, " +
            "SUM(s.sale_price) AS totalSalePrice, " +
            "SUM(s.cost_price_header) AS totalCostPriceHeader " +
            "FROM product p " +
            "JOIN sale s ON p.id = s.productID " +
            "GROUP BY p.id, p.name, p.stock, p.cost_price_header, p.retail_price", nativeQuery = true)
    List<SaleSummary> findAllSalesSummary();
}
