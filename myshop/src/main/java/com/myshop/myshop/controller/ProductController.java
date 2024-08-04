package com.myshop.myshop.controller;

import com.myshop.myshop.entity.Product;
import com.myshop.myshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
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

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (!productOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();
        product.setImage(productDetails.getImage());
        product.setName(productDetails.getName());
        product.setWholesalePrice(productDetails.getWholesalePrice());
        product.setRetailPrice(productDetails.getRetailPrice());
        product.setStock(productDetails.getStock());

        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    
}
