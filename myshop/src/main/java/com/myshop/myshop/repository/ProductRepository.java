package com.myshop.myshop.repository;

import com.myshop.myshop.entity.Product;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods (ถ้ามี) สามารถเพิ่มที่นี่ได้
    List<Product> findByName(String name);
}
