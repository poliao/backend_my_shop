package com.myshop.myshop.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.myshop.myshop.service.CustomerService;

import com.myshop.myshop.service.token.AuthenticationService;
import com.myshop.myshop.service.token.TokenService;
import com.myshop.myshop.entity.*;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class customerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private TokenService tokenService;
    
   

    @PostMapping
    public ResponseEntity<customer> createCustomer(@RequestBody customer customer) {
        customer savedCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<customer>> getAllCustomers() {
        List<customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<customer> getCustomerById(@PathVariable Long id) {
        Optional<customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody customer customer) {
        // เช็คการยืนยันตัวตนของผู้ใช้
        if (authenticationService.authenticate(customer.getUsername(), customer.getPassword())) {
            String token = tokenService.generateToken(customer.getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));

        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam("email") String email) {
        boolean exists = customerService.isEmailExists(email);
        return ResponseEntity.ok(exists);
    }
    
     @PutMapping("/update-by-email")
    public ResponseEntity<customer> updateCustomerByEmail(
        @RequestParam("email") String email, 
        @RequestBody customer updatedCustomer) {
        
        Optional<customer> existingCustomer = customerService.findByEmail(email);
        
        if (existingCustomer.isPresent()) {
            customer customerToUpdate = existingCustomer.get();
            // Update the customer's fields as needed
            customerToUpdate.setPassword(updatedCustomer.getPassword());
            
            // Update other fields...
            
            customer savedCustomer = customerService.createCustomer(customerToUpdate);
            return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
   

}
