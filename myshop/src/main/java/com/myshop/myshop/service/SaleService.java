package com.myshop.myshop.service;

import com.myshop.myshop.entity.Sale;
import com.myshop.myshop.entity.model.SaleSummary;
import com.myshop.myshop.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
     public List<SaleSummary> getSaleSummaryForMonthAndYear(int month, int year) {
        return saleRepository.findSaleSummaryByMonthAndYear(month, year);
    }

    public List<SaleSummary> getAllSalesSummary() {
        return saleRepository.findAllSalesSummary();
    }
}
