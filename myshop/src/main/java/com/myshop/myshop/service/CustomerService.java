package com.myshop.myshop.service;

import com.myshop.myshop.entity.customer;
import com.myshop.myshop.repository.customerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    @Autowired
    private customerRepository customerRepository;

    

    public Optional<customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
    

    public customer createCustomer(customer customer) {
        return customerRepository.save(customer);
    }

    public List<customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public boolean isEmailExists(String email) {
        return customerRepository.existsByEmail(email);
    }

}
