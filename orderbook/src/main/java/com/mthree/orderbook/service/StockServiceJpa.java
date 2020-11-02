package com.mthree.orderbook.service;

import com.mthree.orderbook.entities.Stock;
import com.mthree.orderbook.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceJpa implements StockService {
    private StockRepository stockRepository;

    @Autowired
    public StockServiceJpa(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }

    @Override
    public Stock getStockById(long stockId) {
        return stockRepository.findById(stockId).orElse(null);
    }

    @Override
    public List<Stock> searchStocksByName(String name) {
        return stockRepository.searchByName(name);
    }

    @Override
    public Stock addStock(Stock stock) {
        stockRepository.save(stock);
        return stock;
    }
}
