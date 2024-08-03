package com.myshop.myshop.service.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myshop.myshop.entity.customer;
import com.myshop.myshop.repository.customerRepository;

import java.security.Key;
import java.util.Optional;

@Service
public class TokenService {

    private Key key;

    @Autowired
    private customerRepository customerRepository;

    public TokenService() {
        // Initialize the key directly in the constructor
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(String username) {
        customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", customer.getId());
        claims.put("username", customer.getUsername()); // Use username instead of email
        
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
    }

    public Optional<customer> getCustomerFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long customerId = claims.get("id", Long.class);
        return customerRepository.findById(customerId);
    }
}
