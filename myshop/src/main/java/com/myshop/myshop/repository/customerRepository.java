package com.myshop.myshop.repository;

import  com.myshop.myshop.entity.customer;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface customerRepository extends JpaRepository<customer, Long> {
    Optional<customer> findByUsername(String username);

}
