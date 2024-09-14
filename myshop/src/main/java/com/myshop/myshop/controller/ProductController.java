package com.myshop.myshop.controller;

import com.myshop.myshop.entity.Product;
import com.myshop.myshop.service.FileStorageService;
import com.myshop.myshop.service.ProductService;

import java.io.IOException; // Correct import for IOException
import java.util.Map; // Correct import for Map
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    private final FileStorageService fileStorageService = new FileStorageService();
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/check-name")
    public ResponseEntity<Boolean> checkProductName(@RequestParam("name") String name) {
        List<Product> existingProducts = productService.getProductsByName(name);
        return ResponseEntity.ok(!existingProducts.isEmpty());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}/update-stock")
    public ResponseEntity<Product> updateProductStock(@PathVariable("id") Long id,
            @RequestBody Product productDetails) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (!productOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();
        product.setStock(productDetails.getStock());

        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{id}/update-full")
    public ResponseEntity<Product> updateProductFull(@PathVariable("id") Long id, @RequestBody Product productDetails) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (!productOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();
        product.setRetailPrice(productDetails.getRetailPrice());
        product.setStock(productDetails.getStock());

        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/upload")
    public ResponseEntity<Map<String, List<String>>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        List<String> fileUrls = new ArrayList<>();
        Map<String, List<String>> response = new HashMap<>();
        
        try {
            for (MultipartFile file : files) {
                String fileUrl = fileStorageService.storeFile(file);
                fileUrls.add(fileUrl);
            }
            response.put("fileUrls", fileUrls);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", List.of("Failed to upload files: " + e.getMessage())));
        }
    }
}
