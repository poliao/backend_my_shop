package com.myshop.myshop.controller;

import com.myshop.myshop.entity.Sale;
import com.myshop.myshop.entity.model.SaleSummary;
import com.myshop.myshop.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:4200")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable("id") Long id) {
        Optional<Sale> sale = saleService.getSaleById(id);
        return sale.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        Sale savedSale = saleService.saveSale(sale);
        return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable("id") Long id, @RequestBody Sale sale) {
        if (saleService.getSaleById(id).isPresent()) {
            sale.setId(id);
            Sale updatedSale = saleService.saveSale(sale);
            return new ResponseEntity<>(updatedSale, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable("id") Long id) {
        if (saleService.getSaleById(id).isPresent()) {
            saleService.deleteSale(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sales/summary")
    public List<SaleSummary> getSaleSummary(
        @RequestParam(value = "month", required = false) Integer month,
        @RequestParam(value = "year", required = false) Integer year) {
        
        if (month == null || year == null) {
            // If month or year is null, return all sales summary
            return saleService.getAllSalesSummary();
        } else {
            // If month and year are provided, return summary for the specified month and year
            return saleService.getSaleSummaryForMonthAndYear(month, year);
        }
    }
}
